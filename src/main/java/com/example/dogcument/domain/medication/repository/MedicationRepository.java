package com.example.dogcument.domain.medication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.medication.entity.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
	Optional<Medication> findByName(String name);
}
