package com.example.dogcument.domain.diagnosis.dto;

import java.util.List;

public record IsValid(
	boolean isValid,
	List<String> failedChecks
) {}
