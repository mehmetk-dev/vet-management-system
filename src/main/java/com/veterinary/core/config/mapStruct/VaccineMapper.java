package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.VaccineRequest;
import com.veterinary.dto.response.VaccineResponse;
import com.veterinary.entities.Vaccine;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VaccineMapper {

    VaccineResponse toResponse(Vaccine vaccine);

    Vaccine toEntity(VaccineRequest vaccineRequest);

    void updateEntityFromRequest(@MappingTarget Vaccine entity, VaccineRequest request);
}
