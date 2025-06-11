package com.veterinary.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "animals")
public class Animal extends  BaseEntity{

    private String name;

    private String species;

    private String breed;

    private String gender;

    private String colour;

    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "animal",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Vaccine> vaccines;

    @OneToMany(mappedBy = "animal",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Appointment> appointments;
}
