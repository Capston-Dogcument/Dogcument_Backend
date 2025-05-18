package com.example.dogcument.domain.home.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HomeDogInfoDto {
	private Long id;
	private String name;
	private String gender;
	private Integer age;
	private String profileImg;

	public HomeDogInfoDto(Long id, String name, String gender, Integer age, String profileImg) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.profileImg = profileImg;
	}
}
