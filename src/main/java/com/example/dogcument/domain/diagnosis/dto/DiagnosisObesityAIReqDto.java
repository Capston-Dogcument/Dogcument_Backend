package com.example.dogcument.domain.diagnosis.dto;

import java.util.List;

import com.example.dogcument.domain.dog.entity.Dog;

import lombok.Getter;

@Getter
public class DiagnosisObesityAIReqDto {
	private Long dogId;
	private Double weight;
	private List<String> imgUrls;

	public DiagnosisObesityAIReqDto(Long id, Double weight, List<String> imgUrls) {
		this.dogId = id;
		this.weight = weight;
		this.imgUrls = imgUrls;
	}
}
