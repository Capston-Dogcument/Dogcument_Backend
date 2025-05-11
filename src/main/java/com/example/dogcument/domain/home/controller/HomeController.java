package com.example.dogcument.domain.home.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dogcument.domain.home.dto.HomeResDto;
import com.example.dogcument.domain.home.service.HomeService;

@RestController
public class HomeController {

	private HomeService homeService;

	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}

	@GetMapping("/api/home")
	public ResponseEntity<HomeResDto> getHomeInfo() {
		return ResponseEntity.ok(homeService.getHome());
	}
}
