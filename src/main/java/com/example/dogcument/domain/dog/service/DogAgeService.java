package com.example.dogcument.domain.dog.service;

import org.springframework.stereotype.Service;

import com.example.dogcument.domain.dog.dto.SaveAgeReqDto;
import com.example.dogcument.domain.dog.dto.SaveAgeResDto;
import com.example.dogcument.domain.dog.entity.Dog;
import com.example.dogcument.domain.dog.repository.DogInfoRepository;

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
}
