package com.veterinary.business;

import com.veterinary.core.config.exception.EntityAlreadyExistsException;
import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.AppointmentMapper;
import com.veterinary.dao.AnimalRepo;
import com.veterinary.dao.AppointmentRepo;
import com.veterinary.dao.AvailableDateRepo;
import com.veterinary.dao.DoctorRepo;
import com.veterinary.dto.request.AppointmentRequest;
import com.veterinary.dto.response.AppointmentResponse;
import com.veterinary.entities.Animal;
import com.veterinary.entities.Appointment;
import com.veterinary.entities.Doctor;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper appointmentMapper;
    private final DoctorService doctorService;
    private final AnimalService animalService;
    private final AvailableDateRepo availableDateRepo;

    public AppointmentService(AppointmentRepo appointmentRepo,
                              AppointmentMapper appointmentMapper,
                              DoctorRepo doctorRepo,
                              AnimalRepo animalRepo,
                              DoctorService doctorService,
                              AnimalService animalService,
                              AvailableDateRepo availableDateRepo) {
        this.appointmentRepo = appointmentRepo;
        this.appointmentMapper = appointmentMapper;
        this.doctorService = doctorService;
        this.animalService = animalService;
        this.availableDateRepo = availableDateRepo;
    }

    @Transactional
    public AppointmentResponse save(AppointmentRequest request) {

        Doctor doctor = this.doctorService.getById(request.getDoctorId());
        Animal animal = this.animalService.getById(request.getAnimalId());

        isAvailableDoctor(request);
        isHoursTaken(request);

        Appointment appointment = this.appointmentMapper.toEntity(request);

        return appointmentMapper.toResponse(appointmentRepo.save(appointment));
    }

    private void isHoursTaken(AppointmentRequest request) {
        boolean isHourTaken = appointmentRepo
                .existsByDoctorIdAndAppointmentDate(request.getDoctorId(), request.getAppointmentDate());

        if (isHourTaken) {
            throw new EntityAlreadyExistsException(String.format(
                    ExceptionMessages.DOCTOR_NOT_AVAILABLE_THIS_HOUR, request.getDoctorId(), request.getAppointmentDate()));
        }
    }

    private void isAvailableDoctor(AppointmentRequest request) {

        //        boolean isAvailable = doctor.getAvailableDates().stream()
        //                .anyMatch(date -> date.getAvailable().isEqual(request.getAppointmentDate().toLocalDate()));

        boolean isAvailable = availableDateRepo.existsByDoctorIdAndAvailable(
                request.getDoctorId(), request.getAppointmentDate().toLocalDate());
        if (!isAvailable) {
            throw new EntityAlreadyExistsException(String.format(ExceptionMessages.DOCTOR_NOT_AVAILABLE,
                    request.getDoctorId(), request.getAppointmentDate().toLocalDate()));
        }
    }

    public AppointmentResponse getResponse(long id) {
        return appointmentMapper.toResponse(appointmentRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ExceptionMessages.APPOINTMENT_NOT_FOUND, id))));
    }

    public Appointment getById(long id) {
        return appointmentRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ExceptionMessages.APPOINTMENT_NOT_FOUND, id)));
    }

    public void delete(long id) {
        this.appointmentRepo.delete(this.getById(id));
    }

    public AppointmentResponse update(long id, AppointmentRequest request) {

        Appointment appointment = getById(id);
        Doctor doctor = doctorService.getById(request.getDoctorId());
        Animal animal = animalService.getById(request.getAnimalId());

        isAvailableDoctor(request);
        boolean isHoursTakenForUpdate =
                appointmentRepo.existsByDoctorIdAndAppointmentDateAndIdNot(request.getDoctorId(), request.getAppointmentDate(), id);

        if (isHoursTakenForUpdate) {
            throw new EntityAlreadyExistsException(String.format(
                    ExceptionMessages.DOCTOR_NOT_AVAILABLE_THIS_HOUR, request.getDoctorId(), request.getAppointmentDate()));
        }
        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);
        this.appointmentMapper.updateEntityFromRequest(appointment, request);

        return appointmentMapper.toResponse(appointmentRepo.save(appointment));
    }

    public Page<Appointment> getAllAppointment(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return appointmentRepo.findAll(pageable);
    }

    public List<AppointmentResponse> getAppointmentsByDoctorAndDateRange(long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Appointment> appointments = this.appointmentRepo.findByDoctorIdAndAppointmentDateBetween(doctorId, startDate, endDate);
        if (appointments.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessages.DOCTOR_APPOINTMENT_NOT_FOUND, doctorId, startDate, endDate));
        }
        return appointments.stream().map(appointmentMapper::toResponse).toList();
    }

    public List<AppointmentResponse> getAppointmentsByAnimalAndDateRange(long animalId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Appointment> appointments = this.appointmentRepo.findByAnimalIdAndAppointmentDateBetween(animalId, startDate, endDate);
        if (appointments.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessages.ANIMAL_APPOINTMENT_NOT_FOUND, animalId, startDate, endDate));
        }
        return appointments.stream().map(appointmentMapper::toResponse).toList();
    }
}
