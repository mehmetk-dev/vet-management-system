package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.CustomerRequest;
import com.veterinary.dto.response.CustomerResponse;
import com.veterinary.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(
            target = "animalNames",
            expression = "java(customer.getAnimals() != null ? customer.getAnimals().stream().map(animal -> animal.getName()).toList() : java.util.Collections.emptyList())")
    CustomerResponse toResponse(Customer customer);

    Customer toEntity(CustomerRequest customerRequest);

    void updateEntityFromRequest(@MappingTarget Customer entity, CustomerRequest request);
}
