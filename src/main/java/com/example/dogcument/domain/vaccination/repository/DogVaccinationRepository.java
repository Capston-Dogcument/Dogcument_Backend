package com.example.dogcument.domain.vaccination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.vaccination.entity.DogVaccination;

public interface DogVaccinationRepository extends JpaRepository<DogVaccination, Long> {
	boolean existsByDogId(Long dogId);
}
