package com.example.dogcument.domain.disease.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.disease.entity.Disease;
import com.example.dogcument.domain.disease.entity.DogDisease;
import com.example.dogcument.domain.dog.entity.Dog;

public interface DogDiseaseRepository extends JpaRepository<DogDisease, Long> {
	Boolean existsByDogAndDisease(Dog dog, Disease disease);
	List<DogDisease> findAllByDog(Dog dog);
	void deleteAllByDog(Dog dog);
}
