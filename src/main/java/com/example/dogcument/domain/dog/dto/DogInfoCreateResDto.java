package com.example.dogcument.domain.dog.dto;

import java.time.LocalDate;

import com.example.dogcument.domain.dog.entity.Dog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DogInfoCreateResDto {
	private Long id;
	private String name;
	private String breed;
	private Double weight;
	private LocalDate intakeDate;
	private Dog.Gender gender;

	public static DogInfoCreateResDto from(Dog dog) {
		return DogInfoCreateResDto.builder()
			.id(dog.getId())
			.name(dog.getName())
			.breed(dog.getBreed())
			.weight(dog.getWeight())
			.intakeDate(dog.getIntakeDate())
			.gender(dog.getGender())
			.build();
	}
}
