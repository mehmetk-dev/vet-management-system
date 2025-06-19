package com.veterinary.dao;

import com.veterinary.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> {

    //Belirlenen doktorun o gün müsait olup olmamasını döner
    boolean existsByDoctorIdAndAvailable(Long doctorId, LocalDate availableDate);

    //Belirlenen doktorun çalıştığı günleri döner
    @Query("SELECT a.available FROM AvailableDate a WHERE a.doctor.id = :id")
    List<LocalDate> findAvailableDatesByDoctorId(@Param("id") Long doctorId);


}
