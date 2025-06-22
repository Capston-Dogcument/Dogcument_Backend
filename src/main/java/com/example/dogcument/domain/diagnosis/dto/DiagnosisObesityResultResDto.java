package com.example.dogcument.domain.diagnosis.dto;

import lombok.Getter;

public record DiagnosisObesityResultResDto(
	Long dogId,
	String obesityScore) {}
