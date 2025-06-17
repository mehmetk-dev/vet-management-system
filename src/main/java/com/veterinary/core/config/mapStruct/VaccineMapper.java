package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.VaccineRequest;
import com.veterinary.dto.response.VaccineResponse;
import com.veterinary.entities.Animal;
import com.veterinary.entities.Vaccine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VaccineMapper {

    @Mapping(target = "animalId", source = "animal.id")
    VaccineResponse toResponse(Vaccine vaccine);

    void updateEntityFromRequest(@MappingTarget Vaccine entity, VaccineRequest request);

    @Mapping(target = "animal", source = "animalId", qualifiedByName = "mapAnimalIdToAnimal")
    Vaccine toEntity(VaccineRequest request);

    @Named("mapAnimalIdToAnimal")
    default Animal mapAnimalIdToAnimal(Long id) {
        Animal animal = new Animal();
        animal.setId(id);
        return animal;
    }
}
