package com.example.dogcument.domain.s3;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

	@Value("${aws.s3.bucket}")
	private String bucket;

	@Value("${aws.region}")
	private String region;

	public String uploadFile(MultipartFile file, Long dogId, String photoType) throws IOException {
		String uuid = UUID.randomUUID().toString();

		String originalFilename = file.getOriginalFilename();
		String extension = "";
		int dotIndex = originalFilename.lastIndexOf(".");
		if (dotIndex != -1) {
			extension = originalFilename.substring(dotIndex + 1);
		}

		String filename = dogId + "-" + photoType + "-" + uuid + "." + extension;
		String key = "images/" + URLEncoder.encode(filename, StandardCharsets.UTF_8);

		String contentType = file.getContentType();
		if (contentType == null) {
			contentType = "image/jpeg";
		}

		S3Client s3 = S3Client.builder()
			.region(Region.of(region))
			.credentialsProvider(DefaultCredentialsProvider.create())
			.build();

		s3.putObject(
			PutObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.contentType(contentType)
				.build(),
			RequestBody.fromInputStream(file.getInputStream(), file.getSize())
		);

		return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
	}
}