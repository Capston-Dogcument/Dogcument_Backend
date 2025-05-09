package com.example.dogcument.domain.dog.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import software.amazon.awssdk.annotations.NotNull;

@Getter
public class PredictAgeReqDto {
	private Boolean hasDeciduousTeeth;
	private Integer toothWearLevel;
	private Boolean hasTartar;
	private Boolean hasToothDamage;
	private String eyeColor;
	private Integer grayHairLevelAroundNose;
}
