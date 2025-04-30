package com.example.dogcument.domain.dog.dto;

import java.time.LocalDate;

import com.example.dogcument.domain.dog.entity.Dog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DogInfoCreateReqDto {
	private String name;
	private String breed;
	private Double weight;
	private LocalDate intakeDate;
	private Dog.Gender gender;

	public Dog toEntity() {
		return new Dog(name, breed, gender, intakeDate, weight);
	}
}
