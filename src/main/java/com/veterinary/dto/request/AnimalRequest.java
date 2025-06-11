package com.veterinary.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalRequest {

    @NotNull(message = "Tür boş olamaz")
    @NotBlank(message = "Tür boş olamaz")
    private String name;
    @NotNull(message = "Tür boş olamaz")
    @NotBlank(message = "Tür boş olamaz")
    private String species;
    private String breed;
    @Pattern(regexp = "Erkek|Dişi", message = "Cinsiyet 'Erkek' veya 'Dişi' olmalı")
    private String gender;
    private String colour;
    @PastOrPresent(message = "Doğum tarihi bugünden ileri olamaz")
    private LocalDate dateOfBirth;
    @Min(value = 1, message = "CustomerId geçerli olmalı")
    private int customerId;
}