package com.veterinary.dao;

import com.veterinary.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
