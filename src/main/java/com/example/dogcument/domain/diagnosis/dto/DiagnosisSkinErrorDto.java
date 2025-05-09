package com.example.dogcument.domain.diagnosis.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;

@Getter
public class DiagnosisSkinErrorDto {
	private String detail;

	private List<DiagnosisSkinErrorDto> detailList;

	@Data
	public static class DiagnosisSkinErrorDetail {
		private List<String> loc;
		private String msg;
		private String type;
	}

}
