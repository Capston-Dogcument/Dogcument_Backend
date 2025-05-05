package com.example.dogcument.domain.s3;

import java.io.IOException;
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

	public String uploadFile(MultipartFile file) throws IOException {
		String key = "images/" + UUID.randomUUID() + "_" +
			URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);

		S3Client s3 = S3Client.builder()
			.region(Region.of(region))
			.credentialsProvider(DefaultCredentialsProvider.create())
			.build();

		s3.putObject(
			PutObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.contentType(file.getContentType())
				.acl("public-read")
				.build(),
			RequestBody.fromInputStream(file.getInputStream(), file.getSize())
		);

		return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
	}
}
