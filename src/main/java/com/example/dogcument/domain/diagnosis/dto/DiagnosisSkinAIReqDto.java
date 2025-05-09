package com.example.dogcument.domain.diagnosis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class DiagnosisSkinAIReqDto {
	@JsonProperty("dogId")
	private String dogId;

	@JsonProperty("image_url")
	private String imageUrl;

	public DiagnosisSkinAIReqDto(String dogId, String imageUrl) {
		this.dogId = dogId;
		this.imageUrl = imageUrl;
	}
}
