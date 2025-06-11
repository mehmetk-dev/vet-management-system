package com.veterinary.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "customers")
public class Customer extends BaseEntity{

    private String name;

    private String phone;

    @Column(unique = true,nullable = false)
    private String mail;

    private String city;

    private String address;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<Animal> animals;
}
