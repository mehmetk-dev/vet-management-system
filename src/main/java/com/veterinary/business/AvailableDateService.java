package com.veterinary.business;

import com.veterinary.core.config.exception.BadRequestException;
import com.veterinary.core.config.exception.EntityAlreadyExistsException;
import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.AvailableDateMapper;
import com.veterinary.dao.AvailableDateRepo;
import com.veterinary.dto.request.AvailableDateRequest;
import com.veterinary.dto.response.AvailableDateResponse;
import com.veterinary.entities.AvailableDate;
import com.veterinary.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailableDateService {

    private final AvailableDateRepo availableDateRepo;
    private final AvailableDateMapper availableDateMapper;
    private final DoctorService doctorService;

    public AvailableDateService(AvailableDateRepo availableDateRepo, AvailableDateMapper availableDateMapper, DoctorService doctorService) {
        this.availableDateRepo = availableDateRepo;
        this.availableDateMapper = availableDateMapper;
        this.doctorService = doctorService;
    }

    public AvailableDateResponse save(AvailableDateRequest request) {

        if (availableDateRepo.existsByDoctorIdAndAvailable(request.getDoctorId(), request.getAvailable())) {
            throw new EntityAlreadyExistsException(String.format(ExceptionMessages.DOCTOR_ALREADY_AVAILABLE_ON_DATE, request.getDoctorId(), request.getAvailable()));
        }

        if (request.getAvailable().isBefore(LocalDate.now())) {
            throw new BadRequestException(ExceptionMessages.AVAILABLE_DATE_CANNOT_BE_IN_PAST);
        }

        AvailableDate date = availableDateMapper.toEntity(request);
        Doctor doctor = doctorService.getById(request.getDoctorId());
        date.setDoctor(doctor);

        return availableDateMapper.toResponse(availableDateRepo.save(date));
    }

    public AvailableDateResponse getResponse(long id) {
        return availableDateMapper.toResponse(availableDateRepo.findById(id).orElseThrow(
                () -> new NotFoundException((String.format(ExceptionMessages.AVAILABLE_DATE_NOT_FOUND, id)))));
    }

    public AvailableDate getById(long id) {
        return availableDateRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ExceptionMessages.AVAILABLE_DATE_NOT_FOUND, id)));
    }

    public void delete(long id) {
        this.availableDateRepo.delete(this.getById(id));
    }

    public AvailableDateResponse update(long id, AvailableDateRequest request) {

        AvailableDate availableDate = this.getById(id);

        Long doctorId = request.getDoctorId();

        List<LocalDate> availableList = this.availableDateRepo.findAvailableDatesByDoctorId(doctorId);
        for (LocalDate localDate : availableList) {
            if (request.getAvailable().equals(localDate) && !request.getAvailable().equals(availableDate.getAvailable())) {
                throw new EntityAlreadyExistsException(String.format(ExceptionMessages.DOCTOR_ALREADY_AVAILABLE_ON_DATE, doctorId, request.getAvailable()));
            }
        }

        availableDateMapper.updateEntityFromRequest(availableDate, request);
        availableDate.setDoctor(doctorService.getById(request.getDoctorId()));

        return availableDateMapper.toResponse(availableDateRepo.save(availableDate));
    }

    public Page<AvailableDate> getAllAvailableDates(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return availableDateRepo.findAll(pageable);
    }

}
