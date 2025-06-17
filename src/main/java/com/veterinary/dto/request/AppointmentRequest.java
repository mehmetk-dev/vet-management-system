package com.veterinary.dto.request;

import com.veterinary.validation.HourOnly;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

    @HourOnly
    private LocalDateTime appointmentDate;
    private Long doctorId;
    private Long animalId;
}
