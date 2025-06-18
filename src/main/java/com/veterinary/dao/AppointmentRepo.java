package com.veterinary.dao;

import com.veterinary.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    boolean existsByDoctorIdAndAppointmentDate(long doctorId, LocalDateTime date);
    boolean existsByDoctorIdAndAppointmentDateAndIdNot(long doctorId,LocalDateTime date,long id);

    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId, LocalDateTime startDate, LocalDateTime endDate);

}

