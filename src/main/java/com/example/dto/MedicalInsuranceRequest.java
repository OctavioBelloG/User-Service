package com.example.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class MedicalInsuranceRequest {
    private Long patientId;
    private String policyNumber;
    private String company;
    private String coverage;
    private Date expirationDate;
}
