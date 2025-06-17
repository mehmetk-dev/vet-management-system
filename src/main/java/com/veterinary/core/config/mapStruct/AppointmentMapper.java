package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.AppointmentRequest;
import com.veterinary.dto.response.AppointmentResponse;
import com.veterinary.entities.Animal;
import com.veterinary.entities.Appointment;
import com.veterinary.entities.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "animalId",source = "animal.id")
    @Mapping(target = "doctorId",source = "doctor.id")
    @Mapping(target = "available",source = "appointmentDate")
    AppointmentResponse toResponse(Appointment appointment);

    @Mapping(target = "animal", source = "animalId", qualifiedByName = "mapAnimal")
    @Mapping(target = "doctor", source = "doctorId", qualifiedByName = "mapDoctor")
    Appointment toEntity(AppointmentRequest request);

    void updateEntityFromRequest(@MappingTarget Appointment entity, AppointmentRequest request);

    @Named("mapAnimal")
    default Animal mapAnimal(Long id) {
        if (id == null) return null;
        Animal animal = new Animal();
        animal.setId(id);
        return animal;
    }

    @Named("mapDoctor")
    default Doctor mapDoctor(Long id) {
        if (id == null) return null;
        Doctor doctor = new Doctor();
        doctor.setId(id);
        return doctor;
    }
}
