package domain.vaccination.entity;

import domain.dog.entity.Dog;
import jakarta.persistence.Column;
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
public class DogVaccination {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "dog_id")
	private Dog dog;

	@ManyToOne
	@JoinColumn(name = "vaccination_id")
	private Vaccination vaccination;

	@Column(nullable=false)
	private Boolean status;
}
