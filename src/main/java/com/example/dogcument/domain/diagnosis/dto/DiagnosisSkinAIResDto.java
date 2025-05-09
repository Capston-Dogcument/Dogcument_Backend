package com.example.dogcument.domain.diagnosis.dto;

import java.util.Map;

import lombok.Getter;

@Getter
public class DiagnosisSkinAIResDto {
	private String dogId;
	private Map<String, String> predictions;

}
