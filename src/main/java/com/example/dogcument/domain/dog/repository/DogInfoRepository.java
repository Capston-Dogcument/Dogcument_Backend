package com.example.dogcument.domain.dog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.dog.entity.Dog;

public interface DogInfoRepository extends JpaRepository<Dog, Integer> {
}
