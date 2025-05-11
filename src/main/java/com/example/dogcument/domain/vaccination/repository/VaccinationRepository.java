package com.example.dogcument.domain.vaccination.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.vaccination.entity.Vaccination;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
	Optional<Vaccination> findByName(String name);
}
