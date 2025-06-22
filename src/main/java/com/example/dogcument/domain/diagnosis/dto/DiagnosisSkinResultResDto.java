package com.example.dogcument.domain.diagnosis.dto;

import java.util.List;

import lombok.Getter;


public record DiagnosisSkinResultResDto (
	Long dogId,
	List<String> skinDiseases) {
}
