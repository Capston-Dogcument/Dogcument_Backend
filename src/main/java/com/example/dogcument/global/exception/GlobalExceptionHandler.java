package com.example.dogcument.global.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> exception(Exception e) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "Not Found");
		error.put("message", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Map<String, Object>> handleResponseStatusHandler(ResponseStatusException e) {
		HttpStatus status = HttpStatus.resolve(e.getStatusCode().value());

		Map<String, Object> body = new HashMap<>();
		body.put("error", status != null ? status.getReasonPhrase() : "Error");
		body.put("status", e.getStatusCode().value());
		body.put("message", e.getReason());

		return ResponseEntity.status(e.getStatusCode().value()).body(body);
	}
}
