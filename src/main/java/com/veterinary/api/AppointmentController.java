package com.veterinary.api;

import com.veterinary.business.AppointmentService;
import com.veterinary.core.config.mapStruct.AppointmentMapper;
import com.veterinary.dto.request.AppointmentRequest;
import com.veterinary.dto.response.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {

    private final AppointmentService service;
    private final AppointmentMapper mapper;

    public AppointmentController(AppointmentService service, AppointmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AppointmentResponse> save(@Valid @RequestBody AppointmentRequest request){
        return ResponseEntity.ok(service.save(request));
    }
}
