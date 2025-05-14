package com.example.dogcument.domain.vaccination.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.dogcument.domain.vaccination.Service.DogVaccineService;
import com.example.dogcument.domain.vaccination.dto.VaccinationDto;

@RestController
public class DogVaccineController {

	private final DogVaccineService dogVaccineService;

	public DogVaccineController(DogVaccineService dogVaccineService) {
		this.dogVaccineService = dogVaccineService;
	}

	@GetMapping("/api/vaccination/{dogId}")
	public ResponseEntity<List<VaccinationDto>> getVaccination (@PathVariable Long dogId) {
		return ResponseEntity.ok(dogVaccineService.getVaccineList(dogId));
	}
}
