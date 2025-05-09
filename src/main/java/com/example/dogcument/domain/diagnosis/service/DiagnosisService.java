package com.example.dogcument.domain.diagnosis.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.dogcument.domain.diagnosis.dto.DiagnosisObesityAIResDto;
import com.example.dogcument.domain.diagnosis.dto.DiagnosisObesityResultResDto;
import com.example.dogcument.domain.diagnosis.dto.DiagnosisSkinAIResDto;
import com.example.dogcument.domain.diagnosis.dto.DiagnosisSkinResultResDto;
import com.example.dogcument.domain.diagnosis.dto.ValidateImgsResponse;
import com.example.dogcument.domain.diagnosis.dto.ValidateImgsResDto;
import com.example.dogcument.domain.diagnosis.dto.ValidateSkinAIResDto;
import com.example.dogcument.domain.diagnosis.dto.ValidateSkinImgResDto;
import com.example.dogcument.domain.diagnosis.dto.ValidationResult;
import com.example.dogcument.domain.diagnosis.entity.ObesityImage;
import com.example.dogcument.domain.diagnosis.entity.SkinDiseaseImage;
import com.example.dogcument.domain.diagnosis.repository.ObesityImageRepository;
import com.example.dogcument.domain.diagnosis.repository.SkinDiseaseImageRepository;
import com.example.dogcument.domain.disease.Repository.DiseaseRepository;
import com.example.dogcument.domain.disease.Repository.DogDiseaseRepository;
import com.example.dogcument.domain.disease.entity.Disease;
import com.example.dogcument.domain.disease.entity.DogDisease;
import com.example.dogcument.domain.dog.entity.Dog;
import com.example.dogcument.domain.dog.repository.DogInfoRepository;
import com.example.dogcument.domain.s3.S3Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DiagnosisService {

	private final AIService aiService;
	private final S3Service s3Service;
	private final DogInfoRepository dogInfoRepository;
	private final ObesityImageRepository obesityImageRepository;
	private final SkinDiseaseImageRepository skinDiseaseImageRepository;
	private final DiseaseRepository diseaseRepository;
	private final DogDiseaseRepository dogDiseaseRepository;

	public DiagnosisService(AIService aiService, S3Service s3Service, DogInfoRepository dogInfoRepository,
		ObesityImageRepository obesityImageRepository, SkinDiseaseImageRepository skinDiseaseImageRepository,
		DiseaseRepository diseaseRepository, DogDiseaseRepository dogDiseaseRepository) {
		this.aiService = aiService;
		this.s3Service = s3Service;
		this.dogInfoRepository = dogInfoRepository;
		this.obesityImageRepository = obesityImageRepository;
		this.skinDiseaseImageRepository = skinDiseaseImageRepository;
		this.diseaseRepository = diseaseRepository;
		this.dogDiseaseRepository = dogDiseaseRepository;
	}

	private static final List<String> PHOTO_TYPE_ORDER = List.of("front", "left", "back", "right", "top");

	public ValidateImgsResDto validateAndSaveImgs(List<MultipartFile> images, Long dogId) throws IOException {
		Dog dog = dogInfoRepository.findById(dogId)
			.orElseThrow(()-> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		ValidateImgsResponse validateImgsResponse = aiService.validateImg(images);

		List<String> uploadedUrls = new ArrayList<>();

		for (int i=0; i<images.size(); i++) {
			MultipartFile image = images.get(i);
			String photoType = (i < PHOTO_TYPE_ORDER.size()) ? PHOTO_TYPE_ORDER.get(i) : "unknown";
			String url = s3Service.uploadFile(image, dogId, photoType);
			uploadedUrls.add(url);

			ObesityImage obesityImage = new ObesityImage(dog, url, ObesityImage.Angle.valueOf(photoType.toUpperCase()));

			obesityImageRepository.save(obesityImage);
		}

		return new ValidateImgsResDto(uploadedUrls);
	}

	public DiagnosisObesityResultResDto diagnosisObesity(Long dogId) {
		Dog dog = dogInfoRepository.findById(dogId)
			.orElseThrow(()-> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		List<String> urls = obesityImageRepository.findAllByDogId(dogId).stream()
			.map(ObesityImage::getUrl)
			.toList();

		if (urls.isEmpty())
			throw new EntityNotFoundException("해당 강아지의 사진이 존재하지 않습니다.");

		DiagnosisObesityAIResDto aiResDto = aiService.diagnosisObesity(dog, urls);


		dog.saveObesity(aiResDto.getObesity());
		System.out.println(aiResDto.getObesity());
		dogInfoRepository.save(dog);

		return new DiagnosisObesityResultResDto(dog.getId(), aiResDto.getObesity());
	}

	public ValidateSkinImgResDto validateAndSaveSkinImg(MultipartFile image, Long dogId) throws IOException {
		Dog dog = dogInfoRepository.findById(dogId)
			.orElseThrow(()-> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		ValidateSkinAIResDto validateSkinAIResDto = aiService.validateSkinImg(image);

		String uploadedUrl = "";

		if (validateSkinAIResDto.isValid()) {
			uploadedUrl = s3Service.uploadFile(image, dogId, "skin");

			SkinDiseaseImage skinDiseaseImage = new SkinDiseaseImage(dog, uploadedUrl);

			skinDiseaseImageRepository.save(skinDiseaseImage);
		}
		else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않는 이미지입니다");
		}

		return new ValidateSkinImgResDto(uploadedUrl);
	}

	public DiagnosisSkinResultResDto diagnosisSkinDiseases(Long dogId) {
		Dog dog = dogInfoRepository.findById(dogId)
			.orElseThrow(()-> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		SkinDiseaseImage img = skinDiseaseImageRepository.findByDogId(dogId)
			.orElseThrow(()-> new EntityNotFoundException("피부 질환 이미지가 존재하지 않습니다"));

		DiagnosisSkinAIResDto aiResDto = aiService.diagnosisSkinDisease(dog, img.getUrl());

		System.out.println(aiResDto.getPredictions());

		List<String> diseaseList = new ArrayList<>();

		for (String label: aiResDto.getPredictions().values()) {
			System.out.println(label);
			if (label == null || label.isBlank() || label.equals("무증상")) continue;

			Disease disease = diseaseRepository.findByName(label)
				.orElseGet(() -> diseaseRepository.save(new Disease(label)));

			if (!dogDiseaseRepository.existsByDogAndDisease(dog, disease)) {
				dogDiseaseRepository.save(new DogDisease(dog, disease));
			}

			diseaseList.add(disease.getName());
		}

		return new DiagnosisSkinResultResDto(dog.getId(), diseaseList);
	}
}