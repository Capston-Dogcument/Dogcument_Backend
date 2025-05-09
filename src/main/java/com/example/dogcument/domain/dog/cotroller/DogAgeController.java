package com.example.dogcument.domain.dog.cotroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dogcument.domain.dog.dto.SaveAgeReqDto;
import com.example.dogcument.domain.dog.dto.SaveAgeResDto;
import com.example.dogcument.domain.dog.service.DogAgeService;

@RestController
@RequestMapping("/api/dog/age")
public class DogAgeController {

	private final DogAgeService dogAgeService;

	public DogAgeController(DogAgeService dogAgeService) {
		this.dogAgeService = dogAgeService;
	}

	@PostMapping("/save/{dogId}")
	public ResponseEntity<SaveAgeResDto> saveAge(@PathVariable Long dogId, @RequestBody SaveAgeReqDto reqDto) {
		SaveAgeResDto resDto = dogAgeService.saveAage(dogId, reqDto);
		return ResponseEntity.ok(resDto);
	}
}