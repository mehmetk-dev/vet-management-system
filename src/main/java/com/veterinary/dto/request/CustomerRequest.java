package com.veterinary.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    @NotNull
    private String name;

    @NotNull
    private String phone;

    @Email
    private String email;
    @NotNull
    private String city;
    @NotNull
    private String address;
}
