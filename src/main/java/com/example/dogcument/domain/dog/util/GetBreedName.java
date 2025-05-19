package com.example.dogcument.domain.dog.util;

public class GetBreedName {
	public static String getBreed(String breed) {
		if (breed.equals("POM")) return "포메라니안";
		else if (breed.equals("POO")) return "푸들";
		else if (breed.equals("MAL")) return "말티즈";
		else if (breed.equals("BIC")) return "비숑";
		else return null;
	}
}
