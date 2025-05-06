package com.example.dogcument.domain.diagnosis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.dogcument.domain.diagnosis.dto.DiagnosisObesityResultResDto;
import com.example.dogcument.domain.diagnosis.dto.ValidateImgsResDto;
import com.example.dogcument.domain.diagnosis.service.DiagnosisService;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {

	private final DiagnosisService diagnosisService;

	public DiagnosisController(DiagnosisService diagnosisService) {
		this.diagnosisService = diagnosisService;
	}

	@PostMapping("/obesity/upload")
	public ResponseEntity<?> validateUpload(
		@RequestParam("images") List<MultipartFile> images,
		@RequestParam("dogId") Long dogId) {
		try {
			ValidateImgsResDto resDto = diagnosisService.validateAndSaveImgs(images, dogId);
			return ResponseEntity.ok(resDto);
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 처리 중 오류 발생 (IOException)");
		}
	}

	@PostMapping("/obesity/{dogId}")
	public ResponseEntity<DiagnosisObesityResultResDto> diagnosisObesity(@PathVariable Long dogId) {
		DiagnosisObesityResultResDto resDto = diagnosisService.diagnosisObesity(dogId);
		return ResponseEntity.ok(resDto);
	}
}
