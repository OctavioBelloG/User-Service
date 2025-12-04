package com.example.service;

import java.util.List;

import com.example.dto.MedicalInsuranceRequest;
import com.example.dto.MedicalInsuranceResponse;

public interface MedicalInsuranceService {
   MedicalInsuranceResponse findById(Long id);
    MedicalInsuranceResponse create(MedicalInsuranceRequest req);
    MedicalInsuranceResponse update(Long id, MedicalInsuranceRequest req);
    // List<MedicalInsuranceResponse> findAll(); // Si lo necesitas
    
    // RUTA: GET /insurance/patient/{id}
    List<MedicalInsuranceResponse> getPoliciesByPatientId(Long patientId);
    
    // RUTA: GET /insurance/expired
    List<MedicalInsuranceResponse> getExpiredPolicies();
}
