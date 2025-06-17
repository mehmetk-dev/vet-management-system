package com.veterinary.dao;

import com.veterinary.entities.AvailableDate;
import com.veterinary.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String email);
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByPhone(String phone);

}
