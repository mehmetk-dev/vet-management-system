package com.veterinary.business;

import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.AnimalMapper;
import com.veterinary.core.config.mapStruct.CustomerMapper;
import com.veterinary.dao.AnimalRepo;
import com.veterinary.dao.CustomerRepo;
import com.veterinary.dto.request.AnimalRequest;
import com.veterinary.dto.response.AnimalResponse;
import com.veterinary.dto.response.CustomerResponse;
import com.veterinary.entities.Animal;
import com.veterinary.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepo animalRepo;
    private final AnimalMapper animalMapper;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public AnimalService(AnimalRepo animalRepo, AnimalMapper animalMapper, CustomerService customerService, CustomerMapper customerMapper) {
        this.animalRepo = animalRepo;
        this.animalMapper = animalMapper;
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }


    public AnimalResponse save(AnimalRequest animalRequest) {
        Animal animal = this.animalMapper.toEntity(animalRequest);
        this.animalRepo.save(animal);
        return this.animalMapper.toResponse(animal);
    }

    public Animal getById(long id) {
        return this.animalRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ExceptionMessages.ANIMAL_NOT_FOUND, id)));
    }


    public AnimalResponse update(long id, AnimalRequest animalRequest) {

        Animal animal = this.getById(id);
        Customer customer = customerService.getById(animalRequest.getCustomerId());
        animal.setCustomer(customer);

        animal.setUpdatedAt(LocalDateTime.now());
        animalMapper.updateEntityFromRequest(animal, animalRequest);
        return animalMapper.toResponse(animalRepo.save(animal));
    }

    public void delete(long id) {
        animalRepo.delete(this.getById(id));
    }

    public Page<Animal> getAllAnimals(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return animalRepo.findAll(pageable);
    }

    public List<AnimalResponse> findAllByName(String name) {
        List<Animal> animalList = this.animalRepo.findByNameContainingIgnoreCase(name);

        return animalList.stream().map(
                animalMapper::toResponse).toList();
    }


    public CustomerResponse findCustomerByAnimalId(long id) {
        Customer customer = this.animalRepo.findCustomerByAnimalId(id);
        if (customer == null) {
            throw new NotFoundException(String.format(ExceptionMessages.ANIMAL_NOT_FOUND, id));
        }
        return this.customerMapper.toResponse(customer);
    }
}
