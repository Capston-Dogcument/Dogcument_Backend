package com.example.dogcument.domain.dog.dto;

import java.time.LocalDate;

import com.example.dogcument.domain.dog.entity.Dog;

import lombok.Getter;

@Getter
public class DogInfoUpdateReqDto {
	private String name;
	private String breed;
	private Double weight;
	private LocalDate intakeDate;
	private Dog.Gender gender;
	private Boolean neutered;
	private Integer age;
	private String dogCondition;
}
