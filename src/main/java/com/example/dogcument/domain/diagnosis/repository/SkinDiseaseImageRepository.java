package com.example.dogcument.domain.diagnosis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.diagnosis.entity.SkinDiseaseImage;

public interface SkinDiseaseImageRepository extends JpaRepository<SkinDiseaseImage, Long> {
	Optional<SkinDiseaseImage> findByDogId(Long dogId);
	void deleteAllByDogId(Long dogId);
}
