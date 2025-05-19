package com.example.dogcument.domain.diagnosis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.diagnosis.entity.ObesityImage;

public interface ObesityImageRepository extends JpaRepository<ObesityImage, Long> {
	List<ObesityImage> findAllByDogId(Long dogId);
	void deleteAllByDogId(Long dogId);
	Boolean existsByDogId(Long dogId);
}
