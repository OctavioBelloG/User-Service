package com.example.mapper;

import com.example.model.MedicalInsurance;
import com.example.model.Patient;
import com.example.dto.MedicalInsuranceRequest;
import com.example.dto.MedicalInsuranceResponse;

public final class MedicalInsuranceMapper {

    public static MedicalInsuranceResponse toResponse(MedicalInsurance m) {
        if (m == null) return null;
        return MedicalInsuranceResponse.builder()
                .insuranceId(m.getInsuranceId())
                .patientId(m.getPatient() != null ? m.getPatient().getPatientId() : null)
                .policyNumber(m.getPolicyNumber())
                .company(m.getCompany())
                .coverage(m.getCoverage())
                .expirationDate(m.getExpirationDate())
                .build();
    }

    public static MedicalInsurance toEntity(MedicalInsuranceRequest dto) {
        if (dto == null) return null;
        MedicalInsurance m = new MedicalInsurance();
        m.setPolicyNumber(dto.getPolicyNumber());
        m.setCompany(dto.getCompany());
        m.setCoverage(dto.getCoverage());
        m.setExpirationDate(dto.getExpirationDate());
        if (dto.getPatientId() != null) {
            Patient p = new Patient();
            p.setPatientId(dto.getPatientId());
            m.setPatient(p);
        }
        return m;
    }

    public static void copyToEntity(MedicalInsuranceRequest dto, MedicalInsurance entity) {
        if (dto == null || entity == null) return;
        entity.setPolicyNumber(dto.getPolicyNumber());
        entity.setCompany(dto.getCompany());
        entity.setCoverage(dto.getCoverage());
        entity.setExpirationDate(dto.getExpirationDate());
        if (dto.getPatientId() != null) {
            Patient p = new Patient();
            p.setPatientId(dto.getPatientId());
            entity.setPatient(p);
        } else {
            entity.setPatient(null);
        }
    }
}
