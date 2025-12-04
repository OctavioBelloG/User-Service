package com.example.model;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "MEDICAL_INSURANCE")
public class MedicalInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_insurance_id")
    private Long insuranceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_patient_id")
    private Patient patient;

    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "company")
    private String company;

    @Column(name = "coverage")
    private String coverage;

    @Column(name = "expiration_date")
    private Date expirationDate;
}