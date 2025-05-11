package com.example.dogcument.domain.dogDetail.dto;

import java.util.List;

import com.example.dogcument.domain.disease.dto.DiseaseDto;
import com.example.dogcument.domain.medication.dto.MedicationDto;
import com.example.dogcument.domain.supplement.dto.SupplementDto;
import com.example.dogcument.domain.vaccination.dto.VaccinationDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DogDetailCreateResDto {
	private Long dogId;

	private List<VaccinationDto> vaccination;

	private Boolean isNeutered;

	private List<DiseaseDto> disease;

	private Boolean takesSupplements;
	private List<SupplementDto> supplement;

	private Boolean takesMedication;
	private List<MedicationDto> medication;

	public DogDetailCreateResDto(Long dogId, List<VaccinationDto> vaccination,
		Boolean isNeutered, List<DiseaseDto> disease, Boolean takesSupplements, List<SupplementDto> supplement,
		Boolean takesMedication, List<MedicationDto> medication) {
		this.dogId = dogId;
		this.vaccination = vaccination;
		this.isNeutered = isNeutered;
		this.disease = disease;
		this.takesSupplements = takesSupplements;
		this.supplement = supplement;
		this.takesMedication = takesMedication;
		this.medication = medication;
	}
}
