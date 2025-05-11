package com.example.dogcument.domain.home.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeDogInfoDto {
	private String name;
	private String gender;
	private int age;
	private String profileImg;
}
