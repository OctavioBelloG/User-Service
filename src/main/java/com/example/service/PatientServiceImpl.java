package com.example.service;
import com.example.dto.PatientRequest;
import com.example.dto.PatientResponse;
import com.example.mapper.PatientMapper;
import com.example.model.Patient;
import com.example.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Transactional
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    @Override
    public List<PatientResponse> findAll() {
        return repository.findAll().stream()
                .map(PatientMapper::toResponse)
                .toList();
    }



    // getPatientsPaged()
    @Override
    public List<PatientResponse> getPatientsPaged(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Patient> patients = repository.findAll(pageReq);
        return patients.getContent().stream()
                .map(PatientMapper::toResponse)
                .toList();
    }

// searchPatientsByName()
    @Override
    public List<PatientResponse> searchPatientsByName(String name, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        
        // ❌ ANTES (Esto ya no existe):
        // Page<Patient> patients = repository.findByFullNameContainingIgnoreCase(name, pageReq);

        // ✅ AHORA (Usa el nuevo método):
        Page<Patient> patients = repository.searchByNameOrSurname(name, pageReq);
        
        return patients.getContent().stream()
                .map(PatientMapper::toResponse)
                .toList();
    }
    // getPatientsWithAppointments()
 

    @Override
    public PatientResponse findById(Long patientId) {
        Patient patient = repository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + patientId));


        // Esto le dice a JPA que cargue el objeto User mientras la transacción sigue activa.
        Hibernate.initialize(patient.getUser()); // Necesitas la dependencia de Hibernate

        // Ahora el mapper puede acceder al username sin error
        return PatientMapper.toResponse(patient);
    }

    @Override
    public PatientResponse create(PatientRequest req) {
        if (repository.findByUser_UserId(req.getUserId()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El usuario con ID " + req.getUserId() + " ya está asignado a otro paciente."
            );
        }

        Patient patientToSave = PatientMapper.toEntity(req);


        Patient saved = repository.save(patientToSave);
        return PatientMapper.toResponse(saved);
    }

    @Override
    public PatientResponse update(Long patientId, PatientRequest req) {
        Patient existing = repository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + patientId));


        // Se asegura de que si se intenta cambiar el usuario, el nuevo usuario no esté ya asignado a otro paciente.
        if (!existing.getUser().getUserId().equals(req.getUserId())) {
            if (repository.findByUser_UserId(req.getUserId()).isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El usuario con ID " + req.getUserId() + " ya está asignado a otro paciente."
                );
            }
        }

        PatientMapper.copyToEntity(req, existing);
        Patient saved = repository.save(existing);
        return PatientMapper.toResponse(saved);
    }
}