package com.example.service;

import com.example.dto.PatientRequest;
import com.example.dto.PatientResponse;

import java.util.List;

public interface PatientService {
    //List<PatientResponse> findAll();


    List<PatientResponse> getPatientsPaged(int page, int pageSize); // getPatientsPaged()

    List<PatientResponse> searchPatientsByName(String name, int page, int pageSize); // searchPatientsByName()

    PatientResponse findById(Long patientId);

    List<PatientResponse> findAll();

    PatientResponse create(PatientRequest req);

    PatientResponse update(Long patientId, PatientRequest req);
    
}