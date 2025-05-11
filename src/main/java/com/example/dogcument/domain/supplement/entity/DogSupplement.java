package com.example.dogcument.domain.supplement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.dogcument.domain.dog.entity.Dog;
import jakarta.persistence.Entity;
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
public class DogSupplement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "dog_id")
	private Dog dog;

	@ManyToOne
	@JoinColumn(name = "supplement_id")
	private Supplement supplement;

	private Integer intervalDay;
	private Integer timesPerInterval;

	private LocalDate doseStartDate;
	private LocalDate doseEndDate;


	public DogSupplement(Dog dog, Supplement supplement, int intervalDay, int timesPerInterval
	, LocalDate doseStartDate, LocalDate doseEndDate) {
		this.dog = dog;
		this.supplement = supplement;
		this.intervalDay = intervalDay;
		this.timesPerInterval = timesPerInterval;
		this.doseStartDate = doseStartDate;
		this.doseEndDate = doseEndDate;
	}
}
