package com.example.dogcument.domain.dog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.dog.entity.Dog;

public interface DogInfoRepository extends JpaRepository<Dog, Integer> {
	Optional<Dog> findById(Long id);
}
