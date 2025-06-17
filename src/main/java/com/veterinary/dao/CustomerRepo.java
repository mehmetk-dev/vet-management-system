package com.veterinary.dao;

import com.veterinary.entities.Animal;
import com.veterinary.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    List<Customer> findByNameContainingIgnoreCase(String name);

    @Query("SELECT a.name FROM Animal a WHERE a.customer.id = :id")
    List<String> findAnimalsNameByCustomerId(@Param("id")Long id);

    @Query(value = "SELECT * FROM animals WHERE customer_id = :id", nativeQuery = true)
    List<Animal> findAnimalByCustomerId(@Param("id") Long id);

    
}
