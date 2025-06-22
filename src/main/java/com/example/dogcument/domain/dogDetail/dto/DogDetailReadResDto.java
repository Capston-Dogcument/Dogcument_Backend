package com.example.dogcument.domain.dogDetail.dto;

import java.util.List;

import com.example.dogcument.domain.disease.dto.DiseaseDto;
import com.example.dogcument.domain.dog.entity.Dog;
import com.example.dogcument.domain.medication.dto.MedicationDto;
import com.example.dogcument.domain.supplement.dto.SupplementDto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DogDetailReadResDto {
	private Long dogId;
	private String dogName;

	private Boolean isVaccinated;
	private Boolean isNeutered;
	private List<DiseaseDto> disease;

	private Double dryFoodAmount;
	private Double wetFoodAmount;

	private List<MedicationDto> medication;
	private List<SupplementDto> supplement;

	private Dog.Obesity obesityLevel;
	private Double weight;

	private Integer age;
	private Integer avgAge;

	private String profileImg;
}
