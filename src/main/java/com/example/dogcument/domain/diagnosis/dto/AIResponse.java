package com.example.dogcument.domain.diagnosis.dto;

import java.util.List;

public record AIResponse(
	String message,
	List<ValidationResult> validationResults
) {}
