package com.example.dogcument.domain.dog.service;

import org.springframework.stereotype.Service;

import com.example.dogcument.domain.dog.dto.PredictAgeReqDto;
import com.example.dogcument.domain.dog.dto.PredictAgeResDto;
import com.example.dogcument.domain.dog.dto.SaveAgeReqDto;
import com.example.dogcument.domain.dog.dto.SaveAgeResDto;
import com.example.dogcument.domain.dog.entity.Dog;
import com.example.dogcument.domain.dog.repository.DogInfoRepository;
import com.example.dogcument.domain.dog.util.DogAgeEstimator;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DogAgeService {

	private final DogInfoRepository dogInfoRepository;

	public DogAgeService(DogInfoRepository dogInfoRepository) {
		this.dogInfoRepository = dogInfoRepository;
	}
	public SaveAgeResDto saveAage(Long id, SaveAgeReqDto reqDto) {
		Dog dog = dogInfoRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		if (reqDto.getAge() < 0 || reqDto.getAge() > 40) {
			throw new IllegalArgumentException("옳바르지 않은 나이 값입니다");
		}

		dog.saveAge(reqDto.getAge());

		dogInfoRepository.save(dog);
		return new SaveAgeResDto(dog.getId(), dog.getAge());
	}

	public PredictAgeResDto predictAge(Long id, PredictAgeReqDto reqDto) {
		Dog dog = dogInfoRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		if (reqDto.getToothWearLevel() < 0 || reqDto.getToothWearLevel() > 5) {
			throw new IllegalArgumentException("옳바르지 않은 치아 마모 상태 값입니다");
		}
		if (reqDto.getGrayHairLevelAroundNose() < 0 || reqDto.getGrayHairLevelAroundNose() > 5) {
			throw new IllegalArgumentException("옳바르지 않은 회색 털 수준 값입니다");
		}


		int age = DogAgeEstimator.estimateAge(reqDto);
		dog.saveAge(age);
		dogInfoRepository.save(dog);
		return new PredictAgeResDto(dog.getId(), age);
	}
}
