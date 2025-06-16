package com.veterinary.business;

import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.AppointmentMapper;
import com.veterinary.dao.AppointmentRepo;
import com.veterinary.dto.request.AppointmentRequest;
import com.veterinary.dto.response.AppointmentResponse;
import com.veterinary.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepo appointmentRepo, AppointmentMapper appointmentMapper) {
        this.appointmentRepo = appointmentRepo;
        this.appointmentMapper = appointmentMapper;
    }

    public AppointmentResponse save(AppointmentRequest request){
        Appointment appointment = this.appointmentMapper.toEntity(request);
        return appointmentMapper.toResponse(appointmentRepo.save(appointment));
    }

    public AppointmentResponse getResponse(long id){
        return appointmentMapper.toResponse(appointmentRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND)));
    }

    public Appointment getById(long id){
        return appointmentRepo.findById(id).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND));
    }

    public void delete(long id){
        this.appointmentRepo.delete(this.getById(id));
    }

    public AppointmentResponse update(long id,AppointmentRequest request){

        this.getById(id);

        Appointment updated = appointmentMapper.toEntity(request);
        updated.setId(id);

        return appointmentMapper.toResponse(updated);
    }

    public Page<Appointment> getAllAppointment(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return appointmentRepo.findAll(pageable);
    }

}
