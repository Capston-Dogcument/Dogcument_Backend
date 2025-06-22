package com.example.dogcument.domain.diagnosis.dto;

public record ValidationResult (
	String image,
	IsValid isValid)
{}
