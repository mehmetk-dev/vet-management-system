package com.veterinary.dao;

import com.veterinary.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {

    boolean existsByCode(String code);

    //Belirlenen hayvan ve koda göre aşı listesi döndürür
    List<Vaccine> findByAnimalIdAndCode(Long animalId, String code);

    //Belirlenen hayvanın aşı listesini geri döndürür
    @Query("SELECT v FROM Vaccine v WHERE v.animal.id = :animalId")
    List<Vaccine> findVaccinesByAnimalId(@Param("animalId") long animalId);
}
