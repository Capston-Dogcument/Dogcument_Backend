package com.example.dogcument.domain.diagnosis.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

@Getter
public class DiagnosisObesityAIResDto {
	private String message;
	private String dogId;
	private String obesity;

	@JsonCreator
	public DiagnosisObesityAIResDto(
		@JsonProperty("obesity") String obesity,
		@JsonProperty("message") String message,
		@JsonProperty("dogId") String dogId
	) {
		this.obesity = obesity;
		this.message = message;
		this.dogId = dogId;
	}
}
