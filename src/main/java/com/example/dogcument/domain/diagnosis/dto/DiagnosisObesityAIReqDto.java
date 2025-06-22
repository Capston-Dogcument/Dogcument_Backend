package com.example.dogcument.domain.diagnosis.dto;

import java.util.List;

import com.example.dogcument.domain.dog.entity.Dog;

import lombok.Getter;

@Getter
public class DiagnosisObesityAIReqDto {
	private String dogId;
	private DogInfo dogInfo;
	private List<String> photoUrls;

	@Getter
	public class DogInfo {
		private String breed;
		private double weight;
	}
	public DiagnosisObesityAIReqDto(String id, Dog dog, List<String> urls) {
		this.dogId = id;

		this.dogInfo = new DogInfo();
		this.dogInfo.breed = dog.getBreed();
		this.dogInfo.weight = dog.getWeight();

		this.photoUrls=urls;
	}
}
