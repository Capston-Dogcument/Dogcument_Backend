package com.example.dogcument.domain.dog.service;

import org.springframework.stereotype.Service;

import com.example.dogcument.domain.dog.dto.DogInfoCreateReqDto;
import com.example.dogcument.domain.dog.dto.DogInfoCreateResDto;
import com.example.dogcument.domain.dog.dto.DogInfoReadResDto;
import com.example.dogcument.domain.dog.dto.DogInfoUpdateReqDto;
import com.example.dogcument.domain.dog.entity.Dog;
import com.example.dogcument.domain.dog.repository.DogInfoRepository;

import jakarta.persistence.EntityNotFoundException;

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

	public void deleteDogInfo(Long id) {
		Dog dog = dogInfoRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		dogInfoRepository.delete(dog);
	}
}
