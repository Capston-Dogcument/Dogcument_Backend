package com.example.dogcument.domain.diagnosis.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dogcument.domain.diagnosis.dto.ValidateImgsResponse;
import com.example.dogcument.domain.diagnosis.dto.ValidateImgsResDto;
import com.example.dogcument.domain.diagnosis.dto.ValidationResult;
import com.example.dogcument.domain.diagnosis.entity.ObesityImage;
import com.example.dogcument.domain.diagnosis.repository.ObesityImageRepository;
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

	public DiagnosisService(AIService aiService, S3Service s3Service, DogInfoRepository dogInfoRepository,
		ObesityImageRepository obesityImageRepository) {
		this.aiService = aiService;
		this.s3Service = s3Service;
		this.dogInfoRepository = dogInfoRepository;
		this.obesityImageRepository = obesityImageRepository;
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

			ObesityImage obesityImage = new ObesityImage(dog, url, ObesityImage.Angle.valueOf(photoType));

			obesityImageRepository.save(obesityImage);
		}

		return new ValidateImgsResDto(uploadedUrls);
	}
}