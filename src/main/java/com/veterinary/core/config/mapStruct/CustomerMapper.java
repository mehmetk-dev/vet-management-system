package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.CustomerRequest;
import com.veterinary.dto.response.CustomerResponse;
import com.veterinary.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "animalNames", expression = "java(customer.getAnimals().stream().map(animal -> animal.getName()).toList())")
    CustomerResponse toResponse(Customer customer);

    Customer toEntity(CustomerRequest customerRequest);
}
