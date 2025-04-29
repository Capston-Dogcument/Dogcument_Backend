package domain.dog.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Dog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String breed;

	@Column(nullable=false)
	private LocalDate intakeDate;

	private Boolean neutered;

	@Column(nullable=false)
	private Double weight;

	@Enumerated(EnumType.STRING)
	private Obesity obesityLevel;

	private int age;

	private String condition;

	public enum Obesity {
		과제충, 정상, 저체중
	}
}
