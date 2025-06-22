package com.example.dogcument.domain.dog.util;

import com.example.dogcument.domain.dog.dto.PredictAgeReqDto;

public class DogAgeEstimator {

	public static int estimateAge(PredictAgeReqDto dto) {

		// 유치가 있는 경우
		if (dto.getHasDeciduousTeeth())
			return 0;
		int scoreSum = 0;
		int scoreCount = 0;

		// 치아 마모 상태
		if (dto.getToothWearLevel() != null) {
			scoreSum += dto.getToothWearLevel() + 1;
			scoreCount++;
		}

		// 치석 여부
		if (dto.getHasTartar() != null) {
			scoreSum += dto.getHasTartar() ? 4 : 2;
			scoreCount++;
		}

		// 치아 손상 유무
		if (dto.getHasToothDamage() != null) {
			scoreSum += dto.getHasToothDamage() ? 6 : 3;
			scoreCount++;
		}

		// 회색 털 수준
		if (dto.getGrayHairLevelAroundNose() != null) {
			scoreSum += dto.getGrayHairLevelAroundNose() + 1;
			scoreCount++;
		}

		if (scoreCount == 0) return 1;

		int estimatedAge = Math.round((float) scoreSum / scoreCount);

		return Math.max(estimatedAge, 1);
	}
}
