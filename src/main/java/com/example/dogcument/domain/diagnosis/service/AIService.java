package com.example.dogcument.domain.diagnosis.service;

import static org.springframework.web.servlet.function.RequestPredicates.*;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.dogcument.domain.diagnosis.dto.AIResponse;
import com.example.dogcument.domain.diagnosis.dto.ValidationResult;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AIService {
	String AIServerUrl = "http://116.123.110.162:4000";

	private final WebClient webClient = WebClient.builder().baseUrl(AIServerUrl).build();

	public AIResponse validateImg(List<MultipartFile> images) throws IOException {
		MultipartBodyBuilder builder = new MultipartBodyBuilder();

		for (MultipartFile file : images) {
			builder.part("images", new ByteArrayResource(file.getBytes()) {
				@Override
				public String getFilename() {
					return file.getOriginalFilename();
				}
			}).contentType(MediaType.IMAGE_JPEG);
		}

		MultiValueMap<String, HttpEntity<?>> multipartData = builder.build();

		AIResponse response = webClient.post()
			.uri("/api/ai/validate/photos")
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.bodyValue(multipartData)
			.retrieve()
			.bodyToMono(AIResponse.class)
			.block();

		List<ValidationResult> failedImages = response.validationResults().stream()
			.filter(r -> !r.isValid().isValid())
			.toList();

		if (!failedImages.isEmpty()) {
			StringBuilder errorMsg = new StringBuilder("유효하지 않은 이미지가 존재합니다:\n");

			for (ValidationResult result : failedImages) {
				errorMsg.append("- ")
					.append(result.image())
					.append(": ")
					.append(String.join(", ", result.isValid().failedChecks()))
					.append("\n");
			}

			throw new ResponseStatusException(
				HttpStatus.UNPROCESSABLE_ENTITY,
				errorMsg.toString().trim());
		}

		return response;
	}
}
