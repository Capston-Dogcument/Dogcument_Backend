package com.example.dogcument.domain.dog.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class SaveAgeReqDto {
	private int age;
}
