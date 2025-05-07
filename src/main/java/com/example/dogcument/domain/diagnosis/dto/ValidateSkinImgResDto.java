package com.example.dogcument.domain.diagnosis.dto;

import lombok.Getter;

@Getter
public class ValidateSkinImgResDto {
	private String url;

	public ValidateSkinImgResDto(String url) {
		this.url = url;
	}
}
