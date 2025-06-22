package com.example.dogcument.domain.supplement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.supplement.entity.Supplement;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {
	Optional<Supplement> findByName(String name);
}
