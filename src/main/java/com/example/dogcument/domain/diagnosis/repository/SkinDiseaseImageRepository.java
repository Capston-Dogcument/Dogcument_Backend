package com.example.dogcument.domain.diagnosis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dogcument.domain.diagnosis.entity.SkinDiseaseImage;

public interface SkinDiseaseImageRepository extends JpaRepository<SkinDiseaseImage, Long> {

}
