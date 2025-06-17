package com.veterinary.api;

import com.veterinary.business.AnimalService;
import com.veterinary.business.VaccineService;
import com.veterinary.core.config.mapStruct.AnimalMapper;
import com.veterinary.core.result.ResultData;
import com.veterinary.dto.CursorResponse;
import com.veterinary.dto.request.AnimalRequest;
import com.veterinary.dto.response.AnimalResponse;
import com.veterinary.dto.response.CustomerResponse;
import com.veterinary.dto.response.VaccineResponse;
import com.veterinary.entities.Animal;
import com.veterinary.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;
    private final VaccineService vaccineService;

    public AnimalController(AnimalService animalService, AnimalMapper animalMapper, VaccineService vaccineService) {
        this.animalService = animalService;
        this.animalMapper = animalMapper;
        this.vaccineService = vaccineService;
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
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> getAnimals(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "pageSize",defaultValue = "10") int pageSize
            ){
        Page<Animal> animals = animalService.getAllAnimals(page,pageSize);
        Page<AnimalResponse> animalResponsePage = animals
                .map(animalMapper::toResponse);
        return ResultHelper.cursor(animalResponsePage);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<AnimalResponse>> getAllByName(@RequestParam(name = "name")String name){
        return ResponseEntity.ok(this.animalService.findAllByName(name));
    }

    @GetMapping("/{id}/customer")
    public ResponseEntity<CustomerResponse> getByCustomerName(@PathVariable("id")long id){
        return ResponseEntity.ok(this.animalService.findCustomerByAnimalId(id));
    }

    @GetMapping("/{id}/vaccines")
    public ResponseEntity<List<VaccineResponse>> getVaccinesByAnimalId(@PathVariable("id")long id){
        return ResponseEntity.ok(vaccineService.getVaccinesByAnimalId(id));
    }
}
