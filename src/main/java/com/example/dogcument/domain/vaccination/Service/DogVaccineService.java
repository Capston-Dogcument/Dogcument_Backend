package com.example.dogcument.domain.vaccination.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dogcument.domain.vaccination.dto.VaccinationDto;
import com.example.dogcument.domain.vaccination.repository.DogVaccinationRepository;

@Service
public class DogVaccineService {

	private final DogVaccinationRepository dogVaccinationRepository;

	public DogVaccineService(DogVaccinationRepository dogVaccinationRepository) {
		this.dogVaccinationRepository = dogVaccinationRepository;
	}

	public List<VaccinationDto> getVaccineList(Long dogId) {
		List<VaccinationDto> vaccinationList = dogVaccinationRepository.findAllByDogId(dogId)
			.stream().map(dogVaccination -> new VaccinationDto(dogVaccination.getVaccination())).toList();

		return vaccinationList;
	}
}
