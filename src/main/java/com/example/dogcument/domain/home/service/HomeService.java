package com.example.dogcument.domain.home.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dogcument.domain.dog.repository.DogInfoRepository;
import com.example.dogcument.domain.dog.util.FeedingTimeCalculator;
import com.example.dogcument.domain.home.dto.HomeDogInfoDto;
import com.example.dogcument.domain.home.dto.HomeResDto;
import com.example.dogcument.domain.vaccination.repository.DogVaccinationRepository;

@Service
public class HomeService {

	private DogInfoRepository dogInfoRepository;
	private DogVaccinationRepository dogVaccinationRepository;

	public HomeService(DogInfoRepository dogInfoRepository, DogVaccinationRepository dogVaccinationRepository) {
		this.dogInfoRepository = dogInfoRepository;
		this.dogVaccinationRepository = dogVaccinationRepository;
	}

	public HomeResDto getHome() {
		List<HomeDogInfoDto> homeDogInfoDtoList = dogInfoRepository.findTop10ByOrderByIntakeDateDesc().stream()
			.map(dog -> new HomeDogInfoDto(dog.getId(), dog.getName(), dog.getGender().toString(), dog.getAge(), dog.getProfileImg())).toList();

		long totalDogs = dogInfoRepository.count();

		LocalDateTime nextFeedingTime = FeedingTimeCalculator.getNextFeedingTime();

		double neuteredRate = Math.round((dogInfoRepository.countByNeuteredTrue() / (totalDogs * 1.0)) * 100 * 10) / 10.0;
		double vaccinationRate = Math.round((dogVaccinationRepository.countVaccinatedDogs() / (totalDogs * 1.0)) * 100 * 10) / 10.0;


		return HomeResDto.builder()
			.totalDogs(totalDogs)
			.nextFeedingTime(nextFeedingTime)
			.neuteredRate(neuteredRate)
			.vaccinationRate(vaccinationRate)
			.homeDogList(homeDogInfoDtoList)
			.build();
	}

	public List<HomeDogInfoDto> getHomeDogInfoList() {
		List<HomeDogInfoDto> dogInfoDtoList = dogInfoRepository.findByOrderByIntakeDateDesc().stream()
			.map(dog -> new HomeDogInfoDto(dog.getId(), dog.getName(), dog.getGender().toString(), dog.getAge(), dog.getProfileImg())).toList();

		return dogInfoDtoList;
	}
}
