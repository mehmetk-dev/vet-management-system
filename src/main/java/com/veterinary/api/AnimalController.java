package com.veterinary.api;

import com.veterinary.business.AnimalService;
import com.veterinary.core.config.mapStruct.AnimalMapper;
import com.veterinary.core.result.ResultData;
import com.veterinary.dto.CursorResponse;
import com.veterinary.dto.request.AnimalRequest;
import com.veterinary.dto.response.AnimalResponse;
import com.veterinary.entities.Animal;
import com.veterinary.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    public AnimalController(AnimalService animalService, AnimalMapper animalMapper) {
        this.animalService = animalService;
        this.animalMapper = animalMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AnimalResponse> save(@Valid @RequestBody AnimalRequest animalRequest){
        AnimalResponse savedAnimal = this.animalService.save(animalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnimal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> getById(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(animalMapper.toResponse(this.animalService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponse> update(@PathVariable("id")long id, @RequestBody AnimalRequest animalRequest){
        AnimalResponse response = animalService.update(id, animalRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResultData<CursorResponse<AnimalResponse>> getAnimals(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "pageSize",defaultValue = "10") int pageSize
            ){
        Page<Animal> animals = animalService.getAllAnimals(page,pageSize);
        Page<AnimalResponse> animalResponsePage = animals
                .map(animalMapper::toResponse);
        return ResultHelper.cursor(animalResponsePage);
    }
}
