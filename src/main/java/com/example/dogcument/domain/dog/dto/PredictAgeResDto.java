package com.example.dogcument.domain.dog.dto;

import lombok.Getter;

@Getter
public class PredictAgeResDto {
	private Long dogId;
	private int predictedAge;

	public PredictAgeResDto(Long dogId, int predictedAge) {
		this.dogId = dogId;
		this.predictedAge = predictedAge;
	}
}
