package com.veterinary.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;

    private String name;

    private String phone;

    private String email;

    private String city;

    private String address;

    private List<String> animalNames;
}
