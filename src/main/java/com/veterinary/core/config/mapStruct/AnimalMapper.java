package com.veterinary.core.config.mapStruct;

import com.veterinary.dto.request.AnimalRequest;
import com.veterinary.dto.response.AnimalResponse;
import com.veterinary.entities.Animal;
import com.veterinary.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(target = "customer", source = "customerId")
    Animal toEntity(AnimalRequest animalRequest);

    @Mapping(target = "customerId", source = "customer.id")
    AnimalResponse toResponse(Animal animal);

    default Customer map(Long customerId) {
        if (customerId == null) return null;
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }

    default Long map(Customer customer) {
        if (customer == null) return null;
        return customer.getId();
    }
}
