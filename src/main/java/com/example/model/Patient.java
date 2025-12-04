package com.example.model;

import java.util.List;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PATIENTS")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_patient_id")
    private Long patientId;

    // --- NUEVOS CAMPOS DE NOMBRE ---
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "paternal_surname", nullable = false)
    private String paternalSurname;

    @Column(name = "maternal_surname")
    private String maternalSurname;
    // -------------------------------

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phone;

    // --- NUEVA RELACIÓN DIRECCIÓN (1 a 1) ---
    // CascadeType.ALL permite que al guardar un Paciente, se guarde su Dirección automáticamente
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_address_id", referencedColumnName = "pk_address_id", unique = true) 
    private Address address;
    // ----------------------------------------


    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalInsurance> insurances;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", unique = true, nullable = false)
    private User user;
}