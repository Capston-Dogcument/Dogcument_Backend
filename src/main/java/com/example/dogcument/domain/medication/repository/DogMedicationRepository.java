package com.example.dogcument.domain.medication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.medication.entity.DogMedication;

public interface DogMedicationRepository extends JpaRepository<DogMedication, Long> {
	List<DogMedication> findAllByDogId(Long id);
}
