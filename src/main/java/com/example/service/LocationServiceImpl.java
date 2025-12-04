package com.example.service;

import com.example.dto.LocationRequest;
import com.example.dto.LocationResponse;
import com.example.mapper.LocationMapper;
import com.example.model.Location;
import com.example.model.Patient;
import com.example.repository.LocationRepository;
import com.example.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;
    private final PatientRepository patientRepository;

    @Override
    public LocationResponse findByPatientId(Long patientId) {
        // Validar que el paciente existe
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException("Paciente no encontrado con ID: " + patientId);
        }

        Location location = repository.findByPatient_PatientId(patientId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Ubicación no encontrada para el paciente con ID: " + patientId));

        Hibernate.initialize(location.getPatient());
        return LocationMapper.toResponse(location);
    }

    @Override
    public LocationResponse create(LocationRequest req) {
        // Validar que el paciente existe
        Patient patient = patientRepository.findById(req.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Paciente no encontrado con ID: " + req.getPatientId()));

        // Validar que el paciente no tenga ya una ubicación
        if (repository.findByPatient_PatientId(req.getPatientId()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El paciente ya tiene una ubicación registrada. Use PUT para actualizar."
            );
        }

        Location locationToSave = LocationMapper.toEntity(req);
        locationToSave.setPatient(patient);

        Location saved = repository.save(locationToSave);
        Hibernate.initialize(saved.getPatient());

        return LocationMapper.toResponse(saved);
    }

    @Override
    public LocationResponse update(Long locationId, LocationRequest req) {
        Location existing = repository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Ubicación no encontrada con ID: " + locationId));

        // Si se está cambiando el paciente
        if (!existing.getPatient().getPatientId().equals(req.getPatientId())) {
            Patient newPatient = patientRepository.findById(req.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Paciente no encontrado con ID: " + req.getPatientId()));

            // Validar que el nuevo paciente no tenga ya una ubicación
            if (repository.findByPatient_PatientId(req.getPatientId()).isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El paciente seleccionado ya tiene una ubicación registrada"
                );
            }

            existing.setPatient(newPatient);
        }

        LocationMapper.copyToEntity(req, existing);
        Location saved = repository.save(existing);

        Hibernate.initialize(saved.getPatient());
        return LocationMapper.toResponse(saved);
    }

    @Override
    public List<LocationResponse> findNearby(BigDecimal lat, BigDecimal lng, BigDecimal radius) {
        return repository.findNearby(lat, lng, radius).stream()
                .map(location -> {
                    Hibernate.initialize(location.getPatient());
                    return LocationMapper.toResponse(location);
                })
                .toList();
    }
}