package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.model.MedicalInsurance;
import java.util.List;
import java.sql.Date; // Importar java.sql.Date

public interface MedicalInsuranceRepository extends JpaRepository<MedicalInsurance, Long> {
// RUTA: GET /insurance/patient/{id} -> Ver póliza por ID de paciente
    List<MedicalInsurance> findByPatient_PatientId(Long patientId);

    // RUTA: GET /insurance/expired -> Seguros vencidos (expiration_date < today)
    // El método `findByExpirationDateBefore` usando java.sql.Date funcionará, 
    // pero una Query explícita es más robusta si usas `LocalDate` en el modelo.
    @Query("SELECT m FROM MedicalInsurance m WHERE m.expirationDate < CURRENT_DATE")
    List<MedicalInsurance> findExpiredPolicies();
}
