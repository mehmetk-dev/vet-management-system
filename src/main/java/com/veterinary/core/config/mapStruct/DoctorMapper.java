package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.DoctorRequest;
import com.veterinary.dto.response.DoctorResponse;
import com.veterinary.entities.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorResponse toResponse(Doctor doctor);

    Doctor toEntity(DoctorRequest doctorRequest);
}
