package com.example.repository;

import com.example.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    // Buscar ubicación por patient ID
    Optional<Location> findByPatient_PatientId(Long patientId);

    // Buscar ubicaciones cercanas (usando fórmula de Haversine simplificada)
    @Query("SELECT l FROM Location l WHERE " +
            "ABS(l.latitude - :lat) < :radius AND " +
            "ABS(l.longitude - :lng) < :radius")
    List<Location> findNearby(@Param("lat") BigDecimal lat,
                              @Param("lng") BigDecimal lng,
                              @Param("radius") BigDecimal radius);
}