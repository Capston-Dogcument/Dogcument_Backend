package com.example.dogcument.domain.dog.cotroller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dogcument.domain.dog.dto.DogInfoCreateReqDto;
import com.example.dogcument.domain.dog.dto.DogInfoCreateResDto;

import com.example.dogcument.domain.dog.service.DogInfoService;

@RestController
public class DogInfoController {
	public final DogInfoService dogInfoService;

	public DogInfoController(DogInfoService dogInfoService) {
		this.dogInfoService = dogInfoService;
	}

	@PostMapping("/api/dog/info")
	public ResponseEntity<DogInfoCreateResDto> createDogInfo(@RequestBody DogInfoCreateReqDto reqDto) {
		DogInfoCreateResDto resDto = dogInfoService.createDogInfo(reqDto);
		return ResponseEntity.created(URI.create("/dog/info" + + resDto.getId())).body(resDto);
	}
}
