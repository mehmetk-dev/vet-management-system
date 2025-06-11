package com.veterinary.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequest {

    private String name;

    private String phone;

    private String email;

    private String address;

    private String city;
}
