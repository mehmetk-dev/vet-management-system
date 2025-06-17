package com.veterinary.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.veterinary.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineRequest {
    private String name;

    private String code;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate protectionStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate protectionFinishDate;

    private Long animalId;
}
