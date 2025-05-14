package com.example.dogcument.domain.vaccination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.dogcument.domain.vaccination.entity.DogVaccination;

public interface DogVaccinationRepository extends JpaRepository<DogVaccination, Long> {
	boolean existsByDogId(Long dogId);

	List<DogVaccination> findAllByDogId(Long dogId);

	@Query("SELECT COUNT(DISTINCT dv.dog.id) FROM DogVaccination dv")
	long countVaccinatedDogs();
}
