package com.veterinary.business;

import com.veterinary.core.config.exception.BadRequestException;
import com.veterinary.core.config.exception.EntityAlreadyExistsException;
import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.VaccineMapper;
import com.veterinary.dao.CustomerRepo;
import com.veterinary.dao.VaccineRepo;
import com.veterinary.dto.request.VaccineRequest;
import com.veterinary.dto.response.VaccineResponse;
import com.veterinary.entities.Animal;
import com.veterinary.entities.Vaccine;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccineService {

    private final VaccineMapper vaccineMapper;
    private final VaccineRepo vaccineRepo;
    private final AnimalService animalService;
    private final CustomerRepo customerRepo;

    public VaccineService(VaccineMapper vaccineMapper, VaccineRepo vaccineRepo, AnimalService animalService, CustomerService customerService, CustomerRepo customerRepo) {
        this.vaccineMapper = vaccineMapper;
        this.vaccineRepo = vaccineRepo;
        this.animalService = animalService;
        this.customerRepo = customerRepo;
    }

    @Transactional
    public VaccineResponse save(VaccineRequest request) {

        if (request.getProtectionStartDate().isAfter(LocalDate.now())) {
            throw new BadRequestException(ExceptionMessages.DATE_CANNOT_BE_IN_PAST);
        }

        Animal animal = animalService.getById(request.getAnimalId());

        List<Vaccine> existingVaccines = vaccineRepo.findByAnimalIdAndCode(request.getAnimalId(), request.getCode());

        for (Vaccine vaccine : existingVaccines) {
            if (vaccine.getProtectionFinishDate().isAfter(LocalDate.now())) {
                throw new EntityAlreadyExistsException(String.format(ExceptionMessages.VACCINE_ALL_READY_EXISTS, request.getCode(), vaccine.getProtectionFinishDate()));
            }
        }

        Vaccine vaccine = this.vaccineMapper.toEntity(request);
        vaccine.setAnimal(animal);
        return this.vaccineMapper.toResponse(vaccineRepo.save(vaccine));
    }

    public VaccineResponse getResponse(long id) {
        return vaccineMapper.toResponse(vaccineRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ExceptionMessages.VACCINE_NOT_FOUND, id))));
    }

    public Vaccine get(long id) {
        Vaccine vaccine = vaccineRepo.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(ExceptionMessages.VACCINE_NOT_FOUND, id)));
        Animal animal = this.animalService.getById(vaccine.getAnimal().getId());
        vaccine.setAnimal(animal);
        return vaccine;
    }

    public void delete(long id) {
        this.vaccineRepo.delete(this.get(id));
    }

    public VaccineResponse update(long id, VaccineRequest request) {

        if (request.getProtectionFinishDate().isBefore(LocalDate.now()) ||
                request.getProtectionStartDate().isAfter(request.getProtectionFinishDate())) {
            throw new BadRequestException(ExceptionMessages.DATE_CANNOT_BE_IN_PAST);
        }
        Vaccine existing = this.get(id);

        Animal animal = this.animalService.getById(request.getAnimalId());
        existing.setAnimal(animal);

        vaccineMapper.updateEntityFromRequest(existing, request);
        return vaccineMapper.toResponse(vaccineRepo.save(existing));
    }

    public Page<Vaccine> getAllVaccines(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return vaccineRepo.findAll(pageable);
    }

    public List<VaccineResponse> getVaccinesByAnimalId(long id) {
        List<Vaccine> vaccineList = vaccineRepo.findVaccinesByAnimalId(id);
        if (vaccineList.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessages.ANIMAL_VACCINES_NOT_FOUND, id));
        }
        return vaccineList.stream().map(vaccineMapper::toResponse).toList();
    }

    public List<VaccineResponse> getVaccinesExpiringBetween(LocalDate startDate,LocalDate endDate,long customerId) {
        List<Animal> animalList = this.customerRepo.findAnimalByCustomerId(customerId);
        if (animalList.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessages.CUSTOMER_ANIMALS_NOT_FOUND, customerId));
        }
        return animalList.stream()
                .flatMap(animal -> animal.getVaccines().stream()
                        .filter(vaccine ->
                                !vaccine.getProtectionStartDate().isBefore(startDate) &&
                                        !vaccine.getProtectionFinishDate().isAfter(endDate)))
                .map(vaccineMapper::toResponse)
                .toList();
    }

}
