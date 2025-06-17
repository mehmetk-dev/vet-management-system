package com.veterinary.api;

import com.veterinary.business.DoctorService;
import com.veterinary.core.config.mapStruct.DoctorMapper;
import com.veterinary.core.result.ResultData;
import com.veterinary.dto.CursorResponse;
import com.veterinary.dto.request.DoctorRequest;
import com.veterinary.dto.response.DoctorResponse;
import com.veterinary.entities.Doctor;
import com.veterinary.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    public DoctorController(DoctorService doctorService, DoctorMapper doctorMapper) {
        this.doctorService = doctorService;
        this.doctorMapper = doctorMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DoctorResponse> save(@Valid @RequestBody DoctorRequest request){
        return ResponseEntity.ok(doctorService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(doctorMapper.toResponse(this.doctorService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(@PathVariable("id")long id, @RequestBody DoctorRequest request){
        DoctorResponse response = doctorService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.ok("ID " + id + " olan doktor başarıyla silindi.");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> getDoctors(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "pageSize",defaultValue = "10") int pageSize
    ){
        Page<Doctor> doctors = doctorService.getAllDoctors(page,pageSize);
        Page<DoctorResponse> doctorResponses = doctors
                .map(doctorMapper::toResponse);
        return ResultHelper.cursor(doctorResponses);
    }

}
