package com.example.dogcument.domain.diagnosis.dto;

import java.util.List;

public class ValidateImgsResDto {
	public List<String> urls;

	public ValidateImgsResDto(List<String> uploadedUrls) {
		this.urls = uploadedUrls;
	}
}
