package com.example.dogcument.domain.supplement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.supplement.entity.DogSupplement;

public interface DogSupplementRepository extends JpaRepository<DogSupplement, Long> {
	List<DogSupplement> findAllByDogId(Long id);
}
