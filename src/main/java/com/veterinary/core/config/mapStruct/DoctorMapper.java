package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.DoctorRequest;
import com.veterinary.dto.response.DoctorResponse;
import com.veterinary.entities.AvailableDate;
import com.veterinary.entities.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DoctorMapper {


    @Mapping(target = "availableDate", source = "availableDates", qualifiedByName = "mapAvailableDatesToLocalDate")
    DoctorResponse toResponse(Doctor doctor);

    Doctor toEntity(DoctorRequest doctorRequest);

    void updateEntityFromRequest(@MappingTarget Doctor entity, DoctorRequest request);

    @Named("mapAvailableDatesToLocalDate")
    static List<LocalDate> mapAvailableDatesToLocalDate(List<AvailableDate> dates) {

        if (dates == null) {
            return new ArrayList<>();
        }
        return dates.stream()
                .map(AvailableDate::getAvailable)
                .collect(Collectors.toList());
    }
}
