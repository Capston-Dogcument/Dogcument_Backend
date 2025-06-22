package com.example.dogcument.domain.disease.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.disease.entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
	Optional<Disease> findByName(String name);
}
