package com.veterinary.dto.response;

import com.veterinary.entities.Appointment;
import com.veterinary.entities.AvailableDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {

    private Long id;

    private String name;

    private String phone;

    private String email;

    private String address;

    private String city;

    private List<LocalDate> availableDate;

    private List<LocalDateTime> appointments;
}
