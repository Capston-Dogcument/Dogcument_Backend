package com.example.dogcument.domain.dog.dto;

import java.time.LocalDate;

import com.example.dogcument.domain.dog.entity.Dog;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DogInfoReadResDto {
	private Long id;
	private String name;
	private String breed;
	private Dog.Gender gender;
	private LocalDate intakeDate;
	private Boolean neutered;
	private Double weight;
	private Dog.Obesity obesityLevel;
	private Integer age;
	private String dogCondition;

	public static DogInfoReadResDto from(Dog dog) {
		return DogInfoReadResDto.builder()
			.id(dog.getId())
			.name(dog.getName())
			.breed(dog.getBreed())
			.gender(dog.getGender())
			.intakeDate(dog.getIntakeDate())
			.neutered(dog.getNeutered())
			.weight(dog.getWeight())
			.obesityLevel(dog.getObesityLevel())
			.age(dog.getAge())
			.dogCondition(dog.getDogCondition())
			.build();
	}
}
