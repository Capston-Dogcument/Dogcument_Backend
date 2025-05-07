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
	public DiagnosisObesityAIResDto (
		@JsonProperty("message") String message,
		@JsonProperty("dogId") String dogId,
		@JsonProperty("obesity") String obesity
	) {
		this.message = message;
		this.dogId = dogId;
		this.obesity = obesity;
	}
}