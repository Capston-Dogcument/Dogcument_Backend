package com.example.dogcument.domain.home.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeResDto {
	private Long totalDogs;
	private LocalDateTime nextFeedingTime;

	private Double neuteredRate;
	private Double vaccinationRate;

	private List<HomeDogInfoDto> homeDogList;
}
