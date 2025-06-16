package com.veterinary.dao;

import com.veterinary.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Long> {
    List<Animal>findByNameContainingIgnoreCase(String name);
    List<Animal>findByCustomerName(String name);
}
