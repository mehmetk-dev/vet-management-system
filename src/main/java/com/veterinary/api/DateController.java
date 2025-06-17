package com.veterinary.api;

import com.veterinary.business.AvailableDateService;
import com.veterinary.core.config.mapStruct.AvailableDateMapper;
import com.veterinary.core.result.ResultData;
import com.veterinary.dto.CursorResponse;
import com.veterinary.dto.request.AvailableDateRequest;
import com.veterinary.dto.response.AvailableDateResponse;
import com.veterinary.entities.AvailableDate;
import com.veterinary.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/available-dates")
public class DateController {

    private final AvailableDateService service;
    private final AvailableDateMapper mapper;

    public DateController(AvailableDateService service, AvailableDateMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<AvailableDateResponse> save(@RequestBody @Valid AvailableDateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailableDateResponse> get(@PathVariable("id")long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.getResponse(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id")long id){
        this.service.delete(id);
        return ResponseEntity.ok("Kayıt Başarıyla Silindi.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailableDateResponse> update(@PathVariable("id")long id, @RequestBody AvailableDateRequest request){
        AvailableDateResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResultData<CursorResponse<AvailableDateResponse>> getAllDates(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "pageSize",defaultValue = "10") int pageSize
    ){
        Page<AvailableDate> dates = service.getAllAvailableDates(page,pageSize);
        Page<AvailableDateResponse> dateResponses = dates
                .map(mapper::toResponse);
        return ResultHelper.cursor(dateResponses);
    }
}
