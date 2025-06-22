package com.example.dogcument.domain.dog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.dog.entity.Dog;
import com.example.dogcument.domain.dog.entity.Feeding;

public interface FeedingRepository extends JpaRepository<Feeding, Long> {
	Optional<Feeding> findByDog(Dog dog);
	void deleteAllByDog(Dog dog);
}
