package com.veterinary.api;

import com.veterinary.business.AppointmentService;
import com.veterinary.core.config.mapStruct.AppointmentMapper;
import com.veterinary.core.result.ResultData;
import com.veterinary.dto.CursorResponse;
import com.veterinary.dto.request.AppointmentRequest;
import com.veterinary.dto.response.AppointmentResponse;
import com.veterinary.dto.response.VaccineResponse;
import com.veterinary.entities.Appointment;
import com.veterinary.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    public ResponseEntity<AppointmentResponse> save(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> get(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.service.getResponse(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        this.service.delete(id);
        return ResponseEntity.ok(id + "ID'li kayıt başarıyla silindi.");
    }


    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable("id") long id, @RequestBody AppointmentRequest request) {
        AppointmentResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResultData<CursorResponse<AppointmentResponse>> getAllDAppointments(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Page<Appointment> appointments = service.getAllAppointment(page, pageSize);
        Page<AppointmentResponse> appointmentResponses = appointments
                .map(mapper::toResponse);
        return ResultHelper.cursor(appointmentResponses);
    }

    @GetMapping("/{id}/filter-by-doctor")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByDoctorAndDateRange(
            @PathVariable("id") Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        return ResponseEntity.ok(service.getAppointmentsByDoctorAndDateRange(doctorId, startDate.atStartOfDay(),endDate.atTime(23, 59)));
    }

    @GetMapping("/{id}/filter-by-animal")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByAnimalAndDateRange(
            @PathVariable("id"  ) Long animalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        return ResponseEntity.ok(service.getAppointmentsByAnimalAndDateRange(animalId,startDate.atStartOfDay(),endDate.atTime(23, 59)));
    }
}
