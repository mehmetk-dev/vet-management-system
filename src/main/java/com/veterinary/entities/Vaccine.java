package com.veterinary.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "vaccines")
public class Vaccine extends BaseEntity{

    private String name;

    private String code;

    private LocalDate protectionStartDate;

    private LocalDate protectionFinishDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "animal_id",referencedColumnName = "id")
    private Animal animal;
}
