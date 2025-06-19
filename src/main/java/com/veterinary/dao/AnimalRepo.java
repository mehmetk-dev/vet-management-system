package com.veterinary.dao;

import com.veterinary.entities.Animal;
import com.veterinary.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Long> {

    //Belirli isimdeki hayvanların listesini döner
    List<Animal>findByNameContainingIgnoreCase(String name);

    //Belirli hayvanın sahibini döner
    @Query("SELECT a.customer FROM Animal a WHERE a.id = :id")
    Customer findCustomerByAnimalId(@Param("id") Long id);
}
