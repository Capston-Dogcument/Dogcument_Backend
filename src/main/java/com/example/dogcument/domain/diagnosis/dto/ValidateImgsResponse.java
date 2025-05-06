package com.example.dogcument.domain.diagnosis.dto;

import java.util.List;

public record ValidateImgsResponse(
	String message,
	List<ValidationResult> validationResults
) {}
