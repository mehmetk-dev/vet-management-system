package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.AppointmentRequest;
import com.veterinary.dto.response.AppointmentResponse;
import com.veterinary.entities.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentResponse toResponse(Appointment appointment);

    Appointment toEntity(AppointmentRequest request);
}
