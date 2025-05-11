package com.example.dogcument.domain.supplement.dto;

import com.example.dogcument.domain.supplement.entity.DogSupplement;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SupplementDto {
	private String name;

	private Integer intervalDay;
	private Integer timesPerInterval;

	public SupplementDto(DogSupplement dogSupplement) {
		this.name = dogSupplement.getSupplement().getName();
		this.intervalDay = dogSupplement.getIntervalDay();
		this.timesPerInterval = dogSupplement.getTimesPerInterval();
	}
}
