package com.example.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalInsuranceResponse {
    private Long insuranceId;
    private Long patientId;
    private String policyNumber;
    private String company;
    private String coverage;
    private Date expirationDate;
}
