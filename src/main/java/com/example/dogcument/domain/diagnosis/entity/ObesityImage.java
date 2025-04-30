package com.example.dogcument.domain.diagnosis.entity;

import java.time.LocalDateTime;

import com.example.dogcument.domain.dog.entity.Dog;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ObesityImage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "dog_id")
	private Dog dog;

	private String url;
	private LocalDateTime uploadedDate;
	@Enumerated(EnumType.STRING)
	private Angle angle;

	public enum Angle {
		Top, Left, Right, Front, Back
	}
}
