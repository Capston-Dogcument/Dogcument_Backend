package com.example.dogcument.domain.vaccination.dto;

import com.example.dogcument.domain.vaccination.entity.Vaccination;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VaccinationDto {
	private String name;

	public VaccinationDto(Vaccination vaccination) {
		this.name = vaccination.getName().replaceAll("_", " ");
	}
}
