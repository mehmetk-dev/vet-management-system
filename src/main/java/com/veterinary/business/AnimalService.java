package com.veterinary.business;

import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.AnimalMapper;
import com.veterinary.dao.AnimalRepo;
import com.veterinary.dto.request.AnimalRequest;
import com.veterinary.dto.response.AnimalResponse;
import com.veterinary.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    private final AnimalRepo animalRepo;
    private final AnimalMapper animalMapper;

    public AnimalService(AnimalRepo animalRepo, AnimalMapper animalMapper) {
        this.animalRepo = animalRepo;
        this.animalMapper = animalMapper;
    }

    public AnimalResponse save(AnimalRequest animalRequest) {
        Animal animal = this.animalMapper.toEntity(animalRequest);
        this.animalRepo.save(animal);
        return this.animalMapper.toResponse(animal);
    }

    public Animal getById(long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException("Kayıt Bulunamadı"));
    }


    public AnimalResponse update(long id, AnimalRequest animalRequest) {
        this.getById(id);

        Animal updated = animalMapper.toEntity(animalRequest);
        updated.setId(id);

        Animal saved = animalRepo.save(updated);
        return animalMapper.toResponse(saved);
    }

    public void delete(long id) {
        animalRepo.delete(this.getById(id));
    }

    public Page<Animal> getAllAnimals(int page,int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return animalRepo.findAll(pageable);
    }
}
