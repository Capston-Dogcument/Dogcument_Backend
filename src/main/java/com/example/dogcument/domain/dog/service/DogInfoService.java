package com.example.dogcument.domain.dog.service;

import org.springframework.stereotype.Service;

import com.example.dogcument.domain.diagnosis.repository.ObesityImageRepository;
import com.example.dogcument.domain.diagnosis.repository.SkinDiseaseImageRepository;
import com.example.dogcument.domain.disease.Repository.DogDiseaseRepository;
import com.example.dogcument.domain.dog.dto.DogInfoCreateReqDto;
import com.example.dogcument.domain.dog.dto.DogInfoCreateResDto;
import com.example.dogcument.domain.dog.dto.DogInfoReadResDto;
import com.example.dogcument.domain.dog.dto.DogInfoUpdateReqDto;
import com.example.dogcument.domain.dog.entity.Dog;
import com.example.dogcument.domain.dog.repository.DogInfoRepository;
import com.example.dogcument.domain.dog.repository.FeedingRepository;
import com.example.dogcument.domain.medication.repository.DogMedicationRepository;
import com.example.dogcument.domain.supplement.repository.DogSupplementRepository;
import com.example.dogcument.domain.vaccination.repository.DogVaccinationRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class DogInfoService {
	private final DogInfoRepository dogInfoRepository;
	private final SkinDiseaseImageRepository skinDiseaseImageRepository;
	private final ObesityImageRepository obesityImageRepository;
	private final DogVaccinationRepository dogVaccinationRepository;
	private final DogSupplementRepository dogSupplementRepository;
	private final DogMedicationRepository dogMedicationRepository;
	private final FeedingRepository feedingRepository;
	private final DogDiseaseRepository dogDiseaseRepository;

	public DogInfoService(DogInfoRepository dogInfoRepository, SkinDiseaseImageRepository skinDiseaseImageRepository,
		ObesityImageRepository obesityImageRepository, DogVaccinationRepository dogVaccinationRepository,
		DogSupplementRepository dogSupplementRepository, DogMedicationRepository dogMedicationRepository,
		FeedingRepository feedingRepository, DogDiseaseRepository dogDiseaseRepository) {
		this.dogInfoRepository = dogInfoRepository;
		this.skinDiseaseImageRepository = skinDiseaseImageRepository;
		this.obesityImageRepository = obesityImageRepository;
		this.dogVaccinationRepository = dogVaccinationRepository;
		this.dogSupplementRepository = dogSupplementRepository;
		this.dogMedicationRepository = dogMedicationRepository;
		this.dogDiseaseRepository = dogDiseaseRepository;
		this.feedingRepository = feedingRepository;
	}

	public DogInfoCreateResDto createDogInfo(DogInfoCreateReqDto reqDto) {
		Dog dog = reqDto.toEntity();
		dogInfoRepository.save(dog);
		return DogInfoCreateResDto.from(dog);
	}

	public DogInfoReadResDto readDogInfo(Long id) {
		Dog dog = dogInfoRepository.findById(id)
			.orElseThrow(()-> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));
		return DogInfoReadResDto.from(dog);
	}

	public DogInfoReadResDto updateDogInfo(Long id, DogInfoUpdateReqDto reqDto) {
		Dog dog = dogInfoRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		dog.updateFromDto(reqDto);
		dogInfoRepository.save(dog);
		return DogInfoReadResDto.from(dog);
	}

	@Transactional
	public void deleteDogInfo(Long id) {
		Dog dog = dogInfoRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		skinDiseaseImageRepository.deleteAllByDogId(id);
		obesityImageRepository.deleteAllByDogId(id);
		dogVaccinationRepository.deleteAllByDogId(id);
		dogSupplementRepository.deleteAllByDogId(id);
		dogMedicationRepository.deleteAllByDogId(id);
		dogDiseaseRepository.deleteAllByDog(dog);
		feedingRepository.deleteAllByDog(dog);

		dogInfoRepository.delete(dog);
	}
}
