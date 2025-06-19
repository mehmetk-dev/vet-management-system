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

    //Sadece saat başlarında randevu alınsın diye yazdığım anotasyon
    @HourOnly
    private LocalDateTime appointmentDate;
    private Long doctorId;
    private Long animalId;
}
