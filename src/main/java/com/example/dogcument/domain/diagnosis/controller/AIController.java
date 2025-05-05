package com.example.dogcument.domain.diagnosis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dogcument.domain.diagnosis.dto.AIResponse;
import com.example.dogcument.domain.diagnosis.service.AIService;

@RestController
@RequestMapping("/api")
public class AIController {

	private final AIService aiService;

	public AIController(AIService aiService) {
		this.aiService = aiService;
	}

	@PostMapping(value = "/validate/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> validatePhotos(@RequestParam("images") List<MultipartFile> images) throws
		IOException {

		AIResponse aiResponse = aiService.validateImg(images);
		return ResponseEntity.ok(aiResponse.toString());
	}
}
