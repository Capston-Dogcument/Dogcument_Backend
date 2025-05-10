package com.example.dogcument.domain.dog.util;

import com.example.dogcument.domain.dog.entity.Dog;

public class FeedingCalculator {

	public static class FeedAmount {
		private final double drtFoodAmount;
		private final double wetFoodAmount;

		public FeedAmount(double drtFoodAmount, double wetFoodAmount) {
			this.drtFoodAmount = drtFoodAmount;
			this.wetFoodAmount = wetFoodAmount;
		}

		public double getDrtFoodAmount() {
			return drtFoodAmount;
		}
		public double getWetFoodAmount() {
			return wetFoodAmount;
		}
	}

	public static FeedAmount calculateFeedAmount(Dog dog) {
		double rer = 70 * Math.pow(dog.getWeight(), 0.75);

		double activityFactor = switch (dog.getObesityLevel()) {
			case 마름 -> 1.4;
			case 정상 -> 1.0;
			case 비만 -> 0.8;
		};

		if (dog.getAge() < 1) {
			activityFactor *= 1.2;
		}

		double dailyKcal = rer * activityFactor;

		double dryKcalPerGram = 3.6;
		double wetKcalPerGram = 0.9;

		double dryGrams = Math.round((dailyKcal / dryKcalPerGram) * 10.0) / 10.0;
		double wetGrams = Math.round((dailyKcal / wetKcalPerGram) * 10.0) / 10.0;

		return new FeedAmount(dryGrams, wetGrams);
	}

}
