package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // <--- IMPORTANTE
import com.example.model.Patient;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.paternalSurname) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Patient> searchByNameOrSurname(@Param("name") String name, Pageable pageable);


    Optional<Patient> findByUser_UserId(Long userId);
}