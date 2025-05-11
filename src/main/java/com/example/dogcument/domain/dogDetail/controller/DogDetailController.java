package com.example.dogcument.domain.dogDetail.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dogcument.domain.dogDetail.dto.DogDetailCreateReqDto;
import com.example.dogcument.domain.dogDetail.dto.DogDetailCreateResDto;
import com.example.dogcument.domain.dogDetail.dto.DogDetailReadResDto;
import com.example.dogcument.domain.dogDetail.service.DogDetailService;

@RestController
@RequestMapping("/api/dog")
public class DogDetailController {

	private final DogDetailService dogDetailService;

	public DogDetailController(DogDetailService dogDetailService) {
		this.dogDetailService = dogDetailService;
	}

	@PostMapping("/{dogId}/detail")
	public ResponseEntity<DogDetailCreateResDto> createDogDetail(
		@PathVariable("dogId") Long dogId,
		@RequestBody DogDetailCreateReqDto reqDto) {
		DogDetailCreateResDto resDto = dogDetailService.saveDogDetail(dogId, reqDto);
		return ResponseEntity.created(URI.create("/api/dog/" + dogId + "/detail")).body(resDto);
	}

	@GetMapping("/{dogId}/detail")
	public ResponseEntity<DogDetailReadResDto> readDogDetail(@PathVariable Long dogId) {
		DogDetailReadResDto resDto = dogDetailService.readDogDetail(dogId);
		return ResponseEntity.ok(resDto);
	}
}
