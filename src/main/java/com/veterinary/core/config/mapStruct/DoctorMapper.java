package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.DoctorRequest;
import com.veterinary.dto.response.DoctorResponse;
import com.veterinary.entities.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = DoctorMapperHelper.class)
public interface DoctorMapper {

    @Mapping(target = "availableDate", source = "availableDates", qualifiedByName = "mapAvailableDatesToLocalDate")
    @Mapping(target = "appointments", source = "appointments", qualifiedByName = "mapAppointmentsToLocalDateTime")
    DoctorResponse toResponse(Doctor doctor);

    Doctor toEntity(DoctorRequest doctorRequest);

    void updateEntityFromRequest(@MappingTarget Doctor entity, DoctorRequest request);
}
