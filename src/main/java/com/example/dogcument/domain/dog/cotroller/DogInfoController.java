package com.example.dogcument.domain.dog.cotroller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dogcument.domain.dog.dto.DogInfoCreateReqDto;
import com.example.dogcument.domain.dog.dto.DogInfoCreateResDto;

import com.example.dogcument.domain.dog.dto.DogInfoReadResDto;
import com.example.dogcument.domain.dog.dto.DogInfoUpdateReqDto;
import com.example.dogcument.domain.dog.service.DogInfoService;

@RestController
@RequestMapping("/api/dog/info")
public class DogInfoController {
	public final DogInfoService dogInfoService;

	public DogInfoController(DogInfoService dogInfoService) {
		this.dogInfoService = dogInfoService;
	}

	@PostMapping
	public ResponseEntity<DogInfoCreateResDto> createDogInfo(@RequestBody DogInfoCreateReqDto reqDto) {
		DogInfoCreateResDto resDto = dogInfoService.createDogInfo(reqDto);
		return ResponseEntity.created(URI.create("/dog/info" + + resDto.getId())).body(resDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DogInfoReadResDto> getDogInfo(@PathVariable Long id) {
		DogInfoReadResDto resDto = dogInfoService.readDogInfo(id);
		return ResponseEntity.ok().body(resDto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<DogInfoReadResDto> updateDogInfo(@PathVariable Long id, @RequestBody DogInfoUpdateReqDto reqDto) {
		DogInfoReadResDto resDto = dogInfoService.updateDogInfo(id, reqDto);
		return ResponseEntity.ok().body(resDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDogInfo(@PathVariable Long id) {
		dogInfoService.deleteDogInfo(id);
		return ResponseEntity.noContent().build();
	}
}
