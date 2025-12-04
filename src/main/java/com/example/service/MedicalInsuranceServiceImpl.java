package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.MedicalInsurance;
import com.example.dto.MedicalInsuranceRequest;
import com.example.dto.MedicalInsuranceResponse;
import com.example.mapper.MedicalInsuranceMapper;
import com.example.repository.MedicalInsuranceRepository;
import com.example.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalInsuranceServiceImpl implements MedicalInsuranceService {
private final MedicalInsuranceRepository repository;
    private final PatientRepository patientRepository;
   

    @Override
    public MedicalInsuranceResponse findById(Long id) {
        MedicalInsurance m = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("MedicalInsurance not found: " + id));
        return MedicalInsuranceMapper.toResponse(m);
    }

   @Override
    public MedicalInsuranceResponse create(MedicalInsuranceRequest req) {
        // Validar existencia del paciente
        if (req.getPatientId() != null && !patientRepository.existsById(req.getPatientId())) {
            throw new EntityNotFoundException("Patient not found with ID: " + req.getPatientId());
        }
        MedicalInsurance saved = repository.save(MedicalInsuranceMapper.toEntity(req));
        return MedicalInsuranceMapper.toResponse(saved);
    }
    
    // RUTA: GET /insurance/patient/{id}
    @Override
    public List<MedicalInsuranceResponse> getPoliciesByPatientId(Long patientId) {
        // Verificar si el paciente existe (opcional, si asumimos que la FK es suficiente)
        if (!patientRepository.existsById(patientId)) {
             throw new EntityNotFoundException("Patient not found with ID: " + patientId);
        }
        return repository.findByPatient_PatientId(patientId).stream()
                .map(MedicalInsuranceMapper::toResponse)
                .toList();
    }
    
    // RUTA: GET /insurance/expired
    @Override
    public List<MedicalInsuranceResponse> getExpiredPolicies() {
        return repository.findExpiredPolicies().stream()
                .map(MedicalInsuranceMapper::toResponse)
                .toList();
    }
    @Override
    public MedicalInsuranceResponse update(Long id, MedicalInsuranceRequest req) {
        MedicalInsurance existing = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("MedicalInsurance not found: " + id));
        MedicalInsuranceMapper.copyToEntity(req, existing);
        MedicalInsurance saved = repository.save(existing);
        return MedicalInsuranceMapper.toResponse(saved);
    }
}
