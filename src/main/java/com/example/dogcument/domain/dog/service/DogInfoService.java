package com.example.dogcument.domain.dog.service;

import org.springframework.stereotype.Service;

import com.example.dogcument.domain.dog.dto.DogInfoCreateReqDto;
import com.example.dogcument.domain.dog.dto.DogInfoCreateResDto;
import com.example.dogcument.domain.dog.entity.Dog;
import com.example.dogcument.domain.dog.repository.DogInfoRepository;

@Service
public class DogInfoService {
	private final DogInfoRepository dogInfoRepository;

	public DogInfoService(DogInfoRepository dogInfoRepository) {
		this.dogInfoRepository = dogInfoRepository;
	}

	public DogInfoCreateResDto createDogInfo(DogInfoCreateReqDto reqDto) {
		Dog dog = reqDto.toEntity();
		dogInfoRepository.save(dog);
		return DogInfoCreateResDto.from(dog);
	}
}
