package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ADDRESSES")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_address_id")
    private Long addressId;

    @Column(name = "street")
    private String street;

    @Column(name = "int_number")
    private String intNumber;

    @Column(name = "ext_number")
    private String extNumber;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "city")
    private String city;
    
    @Column(name = "zip_code")
    private String zipCode;

    // Relación inversa para saber a qué paciente pertenece (opcional, bidireccional)
    @OneToOne(mappedBy = "address")
    private Patient patient;
}