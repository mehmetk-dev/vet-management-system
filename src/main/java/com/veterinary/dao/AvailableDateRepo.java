package com.veterinary.dao;

import com.veterinary.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableDateRepo extends JpaRepository<AvailableDate,Long> {
}
