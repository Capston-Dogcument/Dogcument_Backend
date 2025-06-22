package com.example.dogcument.domain.dog.util;

import com.example.dogcument.domain.dog.entity.Dog;

public class AverageAgeGetter {
	public static int getAverageAge(Dog dog) {
		if (dog.getBreed().equals("POM")) return 16;
		if (dog.getBreed().equals("POO")) return 15;
		if (dog.getBreed().equals("MAL")) return 15;
		if (dog.getBreed().equals("BIC")) return 15;
		return 13;
	}
}
