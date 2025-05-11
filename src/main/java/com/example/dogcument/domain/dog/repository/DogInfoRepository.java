package com.example.dogcument.domain.dog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.dogcument.domain.dog.entity.Dog;

public interface DogInfoRepository extends JpaRepository<Dog, Integer> {
	Optional<Dog> findById(Long id);

	List<Dog> findTop10ByOrderByIntakeDateDesc();

	long count();

	Long countByNeuteredTrue();
}
