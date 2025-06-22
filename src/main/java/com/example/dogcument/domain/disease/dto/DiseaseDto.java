package com.example.dogcument.domain.disease.dto;

import com.example.dogcument.domain.disease.entity.Disease;

import lombok.Getter;

@Getter
public class DiseaseDto {
	private String name;

	public DiseaseDto(Disease disease) {
		this.name = disease.getName().replaceAll("_", " ");
	}
}
