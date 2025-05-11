package com.example.dogcument.domain.dogDetail.dto;

import java.util.List;

import com.example.dogcument.domain.medication.dto.MedicationDto;
import com.example.dogcument.domain.supplement.dto.SupplementDto;
import com.example.dogcument.domain.vaccination.dto.VaccinationDto;

import lombok.Getter;

@Getter
public class DogDetailCreateReqDto {
	private String vaccination;

	private Boolean isNeutered;

	private String diseaseInfo;

	private Boolean takesSupplements;
	private List<SupplementDto> supplement;

	private Boolean takesMedication;
	private List<MedicationDto> medication;
}
