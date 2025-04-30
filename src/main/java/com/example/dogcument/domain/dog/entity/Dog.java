package com.example.dogcument.domain.dog.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Dog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String breed;

	@Column(nullable = false)
	private Gender gender;

	@Column(nullable=false)
	private LocalDate intakeDate;

	private Boolean neutered;

	@Column(nullable=false)
	private Double weight;

	@Enumerated(EnumType.STRING)
	private Obesity obesityLevel;

	private Integer age;

	private String dogCondition;

	public Dog(String name, String breed, Gender gender, LocalDate intakeDate, Double weight) {
		this.name = name;
		this.breed = breed;
		this.gender = gender;
		this.intakeDate = intakeDate;
		this.weight = weight;
		this.neutered = null;
		this.age = null;
		this.dogCondition = "";
		this.obesityLevel = null;
	}

	public enum Gender {
		수컷, 암컷
	}
	public enum Obesity {
		과제충, 정상, 저체중
	}
}
