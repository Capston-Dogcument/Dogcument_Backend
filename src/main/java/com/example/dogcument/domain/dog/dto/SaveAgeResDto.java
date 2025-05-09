package com.example.dogcument.domain.dog.dto;

import lombok.Getter;

@Getter
public class SaveAgeResDto {
	private Long dogId;
	private int age;

	public SaveAgeResDto(Long id, int age) {
		this.dogId = id;
		this.age = age;
	}
}
