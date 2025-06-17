package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.AvailableDateRequest;
import com.veterinary.dto.response.AvailableDateResponse;
import com.veterinary.entities.AvailableDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface AvailableDateMapper {

    @Mapping(target = "doctorName", source = "doctor.name")
    AvailableDateResponse toResponse(AvailableDate date);

    @Mapping(target = "doctor.id", source = "doctorId")
    AvailableDate toEntity(AvailableDateRequest request);

    void updateEntityFromRequest(@MappingTarget AvailableDate entity, AvailableDateRequest request);

}
