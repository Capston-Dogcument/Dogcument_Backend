package com.example.dogcument.domain.diagnosis.dto;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.Getter;

@Getter
public class AIErrorResDto {

	private List<Detail> detail;

	@Getter
	public static class Detail {
		private List<Object> loc;
		private String msg;
		private String type;
	}


}
