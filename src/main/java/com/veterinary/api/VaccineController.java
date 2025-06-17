package com.veterinary.api;

import com.veterinary.business.VaccineService;
import com.veterinary.core.config.mapStruct.VaccineMapper;
import com.veterinary.core.result.ResultData;
import com.veterinary.dto.CursorResponse;
import com.veterinary.dto.request.VaccineRequest;
import com.veterinary.dto.response.VaccineResponse;
import com.veterinary.entities.Vaccine;
import com.veterinary.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {

    private final VaccineService vaccineService;
    private final VaccineMapper vaccineMapper;

    public VaccineController(VaccineService vaccineService, VaccineMapper vaccineMapper) {
        this.vaccineService = vaccineService;
        this.vaccineMapper = vaccineMapper;
    }

    @PostMapping
    public ResponseEntity<VaccineResponse> save(@RequestBody @Valid VaccineRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.vaccineService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccineResponse> get(@PathVariable("id")long id){
        return ResponseEntity.ok(vaccineMapper.toResponse(vaccineService.get(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccineResponse> update(@PathVariable("id")long id, @RequestBody VaccineRequest request){
        VaccineResponse response = vaccineService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        vaccineService.delete(id);
        return ResponseEntity.ok("ID " + id + " olan doktor başarıyla silindi.");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> getAllVaccines(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "pageSize",defaultValue = "10") int pageSize
    ){
        Page<Vaccine> vaccines = vaccineService.getAllVaccines(page,pageSize);
        Page<VaccineResponse> vaccineResponses = vaccines
                .map(vaccineMapper::toResponse);
        return ResultHelper.cursor(vaccineResponses);
    }


}
