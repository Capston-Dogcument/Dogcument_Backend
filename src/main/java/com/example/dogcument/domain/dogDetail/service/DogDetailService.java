package com.example.dogcument.domain.dogDetail.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dogcument.domain.diagnosis.entity.ObesityImage;
import com.example.dogcument.domain.diagnosis.repository.ObesityImageRepository;
import com.example.dogcument.domain.disease.Repository.DiseaseRepository;
import com.example.dogcument.domain.disease.Repository.DogDiseaseRepository;
import com.example.dogcument.domain.disease.dto.DiseaseDto;
import com.example.dogcument.domain.disease.entity.Disease;
import com.example.dogcument.domain.disease.entity.DogDisease;
import com.example.dogcument.domain.dog.entity.Dog;
import com.example.dogcument.domain.dog.entity.Feeding;
import com.example.dogcument.domain.dog.repository.DogInfoRepository;
import com.example.dogcument.domain.dog.repository.FeedingRepository;
import com.example.dogcument.domain.dog.util.AverageAgeGetter;
import com.example.dogcument.domain.dog.util.FeedingCalculator;
import com.example.dogcument.domain.dogDetail.dto.DogDetailCreateReqDto;
import com.example.dogcument.domain.dogDetail.dto.DogDetailCreateResDto;
import com.example.dogcument.domain.dogDetail.dto.DogDetailReadResDto;
import com.example.dogcument.domain.medication.dto.MedicationDto;
import com.example.dogcument.domain.medication.entity.DogMedication;
import com.example.dogcument.domain.medication.entity.Medication;
import com.example.dogcument.domain.medication.repository.DogMedicationRepository;
import com.example.dogcument.domain.medication.repository.MedicationRepository;
import com.example.dogcument.domain.supplement.dto.SupplementDto;
import com.example.dogcument.domain.supplement.entity.DogSupplement;
import com.example.dogcument.domain.supplement.entity.Supplement;
import com.example.dogcument.domain.supplement.repository.DogSupplementRepository;
import com.example.dogcument.domain.supplement.repository.SupplementRepository;
import com.example.dogcument.domain.vaccination.dto.VaccinationDto;
import com.example.dogcument.domain.vaccination.entity.DogVaccination;
import com.example.dogcument.domain.vaccination.entity.Vaccination;
import com.example.dogcument.domain.vaccination.repository.DogVaccinationRepository;
import com.example.dogcument.domain.vaccination.repository.VaccinationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DogDetailService {

	private final DogInfoRepository dogInfoRepository;
	private final DogDiseaseRepository dogDiseaseRepository;
	private final DiseaseRepository diseaseRepository;
	private final VaccinationRepository vaccinationRepository;
	private final DogVaccinationRepository dogVaccinationRepository;
	private final SupplementRepository supplementRepository;
	private final DogSupplementRepository dogSupplementRepository;
	private final MedicationRepository medicationRepository;
	private final DogMedicationRepository dogMedicationRepository;
	private final FeedingRepository feedingRepository;
	private final ObesityImageRepository obesityImageRepository;

	public DogDetailService(DogInfoRepository dogInfoRepository, DogDiseaseRepository dogDiseaseRepository,
		DiseaseRepository diseaseRepository, VaccinationRepository vaccinationRepository,
		DogVaccinationRepository dogVaccinationRepository, SupplementRepository supplementRepository,
		DogSupplementRepository dogSupplementRepository, MedicationRepository medicationRepository,
		DogMedicationRepository dogMedicationRepository, FeedingRepository feedingRepository,
		ObesityImageRepository obesityImageRepository) {
		this.dogDiseaseRepository = dogDiseaseRepository;
		this.dogInfoRepository = dogInfoRepository;
		this.diseaseRepository = diseaseRepository;
		this.vaccinationRepository = vaccinationRepository;
		this.dogVaccinationRepository = dogVaccinationRepository;
		this.supplementRepository = supplementRepository;
		this.dogSupplementRepository = dogSupplementRepository;
		this.medicationRepository = medicationRepository;
		this.dogMedicationRepository = dogMedicationRepository;
		this.feedingRepository = feedingRepository;
		this.obesityImageRepository = obesityImageRepository;
	}

	public DogDetailCreateResDto saveDogDetail(Long dogId, DogDetailCreateReqDto reqDto) {
		Dog dog = dogInfoRepository.findById(dogId)
			.orElseThrow(()-> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		// 중성화 여부
		dog.saveNeutered(reqDto.getIsNeutered());


		// 질병 정보
		List<DiseaseDto> diseaseDtoList = new ArrayList<>();
		if (reqDto.getDiseaseInfo() != null && !reqDto.getDiseaseInfo().isBlank()) {
			String[] diseaseNames = reqDto.getDiseaseInfo().split(",\\s");


			for (String rawName : diseaseNames) {
				String name = rawName.trim().replaceAll("\\s+", "_");

				Disease disease = diseaseRepository.findByName(name)
					.orElseGet(() -> diseaseRepository.save(new Disease(name)));

				diseaseDtoList.add(new DiseaseDto(disease));

				// dogDisease 추가
				dogDiseaseRepository.save(new DogDisease(dog, disease));
			}
		}

		// 백신 정보
		List<VaccinationDto> vaccinationList = new ArrayList<>();
		if (reqDto.getVaccination() != null && !reqDto.getVaccination().isBlank()) {
			String[] vaccinationNames = reqDto.getVaccination().split(",\\s");

			for(String rawName : vaccinationNames) {
				String name = rawName.trim().replaceAll("\\s+", "_");

				Vaccination vaccination = vaccinationRepository.findByName(name)
					.orElseGet(() -> vaccinationRepository.save(new Vaccination(name)));

				vaccinationList.add(new VaccinationDto(vaccination));

				dogVaccinationRepository.save(new DogVaccination(dog, vaccination));
			}
		}

		// 영양제
		List<SupplementDto> supplementDtoList = new ArrayList<>();
		if (reqDto.getTakesSupplements()) {
			for (SupplementDto s : reqDto.getSupplement()) {
				String supplementName = s.getName().replaceAll("\\s+", "_");
				Supplement supplement = supplementRepository.findByName(supplementName)
					.orElseGet(() -> supplementRepository.save(new Supplement(supplementName)));

				DogSupplement dogSupplement = new DogSupplement(dog, supplement, s.getIntervalDay(), s.getTimesPerInterval(), s.getDoseStartDate(), s.getDoseEndDate());
				dogSupplementRepository.save(dogSupplement);

				supplementDtoList.add(new SupplementDto(dogSupplement));

			}
		}

		// 약
		List<MedicationDto> medicationDtoList = new ArrayList<>();
		if (reqDto.getTakesMedication()) {
			for (MedicationDto m : reqDto.getMedication()) {
				String medicationName = m.getName().replaceAll("\\s+", "_");
				Medication medication = medicationRepository.findByName(medicationName)
					.orElseGet(() -> medicationRepository.save(new Medication(medicationName)));

				DogMedication dogMedication = new DogMedication(dog, medication, m.getIntervalDay(), m.getTimesPerInterval(), m.getDoseStartDate(), m.getDoseEndDate());
				dogMedicationRepository.save(dogMedication);

				medicationDtoList.add(new MedicationDto(dogMedication));
			}
		}

		// 프로필 이미지 생성
		if(dog.getProfileImg() == null) {
			boolean isExist = obesityImageRepository.existsById(dog.getId());
			if (isExist) {
				List<ObesityImage> obesityImageList = obesityImageRepository.findAllByDogId(dog.getId());
				for (ObesityImage obesityImage : obesityImageList) {
					if (obesityImage.getAngle() == ObesityImage.Angle.FRONT) {
						dog.saveProfileImg(obesityImage.getUrl());
						dogInfoRepository.save(dog);
					}
				}
			}
		}

		return DogDetailCreateResDto.builder()
			.dogId(dog.getId())
			.isNeutered(dog.getNeutered())
			.disease(diseaseDtoList)
			.vaccination(vaccinationList)
			.takesSupplements(reqDto.getTakesSupplements())
			.supplement(supplementDtoList)
			.takesMedication(reqDto.getTakesMedication())
			.medication(medicationDtoList)
			.build();
	}

	public DogDetailReadResDto readDogDetail(Long dogId) {
		Dog dog = dogInfoRepository.findById(dogId)
			.orElseThrow(()-> new EntityNotFoundException("해당 ID의 강아지를 찾을 수 없습니다"));

		Boolean isVaccinated = dogVaccinationRepository.existsByDogId(dog.getId());

		List<DiseaseDto> diseaseList = dogDiseaseRepository.findAllByDog(dog).stream()
			.map(dd -> new DiseaseDto(dd.getDisease())).toList();

		List<MedicationDto> medicationList = dogMedicationRepository.findAllByDogId(dog.getId())
			.stream().map(MedicationDto::new).toList();

		List<SupplementDto> supplementList = dogSupplementRepository.findAllByDogId(dog.getId())
			.stream().map(SupplementDto::new).toList();

		Feeding feeding = feedingRepository.findByDog(dog)
			.orElse(null);

		if (feeding == null) {
			FeedingCalculator.FeedAmount calculated = FeedingCalculator.calculateFeedAmount(dog);
			feeding = new Feeding(dog, calculated.getDryFoodAmount(), calculated.getWetFoodAmount());
			feedingRepository.save(feeding);
		}

		int avgAge = AverageAgeGetter.getAverageAge(dog);

		return DogDetailReadResDto.builder()
			.dogId(dog.getId())
			.dogName(dog.getName())
			.isVaccinated(isVaccinated)
			.isNeutered(dog.getNeutered())
			.disease(diseaseList)
			.dryFoodAmount(feeding.getDryFoodAmount())
			.wetFoodAmount(feeding.getWetFoodAmount())
			.medication(medicationList)
			.supplement(supplementList)
			.obesityLevel(dog.getObesityLevel())
			.weight(dog.getWeight())
			.age(dog.getAge())
			.avgAge(avgAge)
			.profileImg(dog.getProfileImg())
			.build();
	}
}
