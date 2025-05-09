package com.example.dogcument.domain.diagnosis.service;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.dogcument.domain.diagnosis.dto.DiagnosisObesityAIReqDto;
import com.example.dogcument.domain.diagnosis.dto.AIErrorResDto;
import com.example.dogcument.domain.diagnosis.dto.DiagnosisObesityAIResDto;
import com.example.dogcument.domain.diagnosis.dto.DiagnosisSkinAIReqDto;
import com.example.dogcument.domain.diagnosis.dto.DiagnosisSkinAIResDto;
import com.example.dogcument.domain.diagnosis.dto.ValidateImgsResponse;
import com.example.dogcument.domain.diagnosis.dto.ValidateSkinAIResDto;
import com.example.dogcument.domain.diagnosis.dto.ValidationResult;
import com.example.dogcument.domain.dog.entity.Dog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIService {
	String AIServerUrl = "http://116.123.110.162:4000";

	private final RestTemplate restTemplate;
	private final WebClient webClient = WebClient.builder().baseUrl(AIServerUrl).build();

	public ValidateImgsResponse validateImg(List<MultipartFile> images) throws IOException {
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

		ValidateImgsResponse response = webClient.post()
			.uri("/api/ai/validate/photos")
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.bodyValue(multipartData)
			.retrieve()
			.bodyToMono(ValidateImgsResponse.class)
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

	public DiagnosisObesityAIResDto diagnosisObesity(Dog dog, List<String> imgUrls) {
		DiagnosisObesityAIReqDto reqDto = new DiagnosisObesityAIReqDto(dog.getId().toString(), dog, imgUrls);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(
				AIServerUrl + "/api/ai/obesity/predict",
				reqDto, String.class
			);

			String json = response.getBody();

			System.out.println(json);

			ObjectMapper objectMapper = new ObjectMapper();
			DiagnosisObesityAIResDto resDto = objectMapper.readValue(json, DiagnosisObesityAIResDto.class);
			return resDto;

		} catch (HttpClientErrorException.UnprocessableEntity e) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				AIErrorResDto error = objectMapper.readValue(e.getResponseBodyAsString(), AIErrorResDto.class);
				String errorMsg = error.getDetail().stream()
					.map(AIErrorResDto.Detail::getMsg)
					.collect(Collectors.joining(", "));
				throw new RuntimeException("AI 서버 오류: " + errorMsg);
			} catch (IOException ex) {
				System.out.println(e.getResponseBodyAsString());
				throw new RuntimeException("AI 오류 응답 파싱 실패" + ex.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException("AI 서버 요청 실패: " + e.getMessage(), e);
		}
	}

	//predict_url = "https://GyeongJae-Dogcument-Skin-Disease-AI.hf.space/predict"
	// validate_url = "https://GyeongJae-Dogcument-Skin-Disease-AI.hf.space/validate-image"
	String skinAIServer = "https://GyeongJae-Dogcument-Skin-Disease-AI.hf.space";

	private final WebClient skinWebClient = WebClient.builder().baseUrl(skinAIServer).build();


	public ValidateSkinAIResDto validateSkinImg(MultipartFile image) throws IOException {
		MultipartBodyBuilder builder = new MultipartBodyBuilder();

		builder.part("image", new ByteArrayResource(image.getBytes()) {
			@Override
			public String getFilename() {
				return image.getOriginalFilename();
			}
		}).contentType(MediaType.IMAGE_JPEG);

		MultiValueMap<String, HttpEntity<?>> multipartData = builder.build();

		ValidateSkinAIResDto resDto = skinWebClient.post()
			.uri("/validate-image")
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.body(BodyInserters.fromMultipartData(multipartData))
			.retrieve()
			.onStatus(status -> status.value() == 422, clientResponse ->
				clientResponse.bodyToMono(String.class).map(
					errorBody -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, errorBody)
				))
			.bodyToMono(ValidateSkinAIResDto.class)
			.block();

		return resDto;
	}


	public DiagnosisSkinAIResDto diagnosisSkinDisease(Dog dog, String imgUrl) {
		DiagnosisSkinAIReqDto reqDto = new DiagnosisSkinAIReqDto(dog.getId().toString(), imgUrl);

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<DiagnosisSkinAIReqDto> request = new HttpEntity<>(reqDto, headers);

			ResponseEntity<DiagnosisSkinAIResDto> response = restTemplate.postForEntity(
				skinAIServer + "/predict",
				request, DiagnosisSkinAIResDto.class
			);
			return response.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			System.out.println(e.getMessage());
			String errorJson = e.getResponseBodyAsString();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode node = objectMapper.readTree(errorJson);

				if (node.has("detail")) {
					String message = node.get("detail").asText();
					throw new RuntimeException("AI 서버 응답 오류: " + message);
				} else if (node.get("detail").isArray()) {
					StringBuilder errorMsg = new StringBuilder("AI 필드 오류: \n");
					for(JsonNode err: node.get("detail")) {
						errorMsg.append("- ")
							.append(String.join(".", StreamSupport
								.stream(err.get("loc").spliterator(), false)
								.map(JsonNode::asText).toList()))
							.append(": ")
							.append(err.get("msg").asText())
							.append("\n");
					}
					throw new RuntimeException(errorMsg.toString());
				}
				throw new RuntimeException("알 수 없는 응답 형식: " + errorJson);
			} catch (JsonProcessingException ex) {
				throw new RuntimeException("응답 파싱 실패: " + errorJson, ex);
			}
		}
	}
}
