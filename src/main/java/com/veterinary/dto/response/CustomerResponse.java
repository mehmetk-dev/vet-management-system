package com.veterinary.dto.response;

import com.veterinary.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;

    private String name;

    private String phone;

    private String email;

    private String city;

    private String address;

    private List<Animal> animals;
}
