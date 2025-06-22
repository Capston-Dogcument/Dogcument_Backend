package com.example.dogcument.domain.medication.dto;

import java.time.LocalDate;

import com.example.dogcument.domain.medication.entity.DogMedication;
import com.example.dogcument.domain.medication.entity.Medication;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MedicationDto {
	private String name;

	private Integer intervalDay;
	private Integer timesPerInterval;

	private LocalDate doseStartDate;
	private LocalDate doseEndDate;

	public MedicationDto(DogMedication dogMedication) {
		this.name = dogMedication.getMedication().getName();
		this.intervalDay = dogMedication.getIntervalDay();
		this.timesPerInterval = dogMedication.getTimesPerInterval();
		this.doseStartDate = dogMedication.getDoseStartDate();
		this.doseEndDate = dogMedication.getDoseEndDate();
	}
}
