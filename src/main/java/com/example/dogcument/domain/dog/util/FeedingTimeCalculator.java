package com.example.dogcument.domain.dog.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class FeedingTimeCalculator {
	private static final List<LocalTime> FEEDING_TIME = List.of(
		LocalTime.of(8, 0),
		LocalTime.of(18, 0)
	);

	public static LocalDateTime getNextFeedingTime() {
		LocalDateTime now = LocalDateTime.now();

		for(LocalTime feedingTime : FEEDING_TIME) {
			LocalDateTime todayFeeding = now.toLocalDate().atTime(feedingTime);
			if (todayFeeding.isAfter(now)) {
				return todayFeeding;
			}
		}
		return now.toLocalDate().plusDays(1).atTime(FEEDING_TIME.get(0));
	}
}
