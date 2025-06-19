package com.veterinary.dao;

import com.veterinary.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {

    //Belirli bir doktorun girilen tarihte randevusu olup olmadığını kontrol eder
    boolean existsByDoctorIdAndAppointmentDate(long doctorId, LocalDateTime date);

    // Belirli bir doktorun, belirtilen tarihte ve belirtilen ID dışındaki randevularını kontrol eder (güncelleme için)
    boolean existsByDoctorIdAndAppointmentDateAndIdNot(long doctorId,LocalDateTime date,long id);

    // Belirli bir dokturun, iki tarih arasındaki tüm randevularını döner
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);

    // Belirli bir hayvanın, iki tarih arasındaki tüm randevularını döner
    List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId, LocalDateTime startDate, LocalDateTime endDate);

}

