package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import java.sql.Date;

@Value
@Builder
public class PatientResponse {

    @JsonProperty("id of the patient")
    private Long patientId;

    @JsonProperty("first name")
    private String firstName;

    @JsonProperty("paternal surname")
    private String paternalSurname;

    @JsonProperty("maternal surname")
    private String maternalSurname;

    @JsonProperty("birth date")
    private Date birthDate;

    @JsonProperty("address details")
    private AddressDto address;

    @JsonProperty("phone number")
    private String phone;

    @JsonProperty("user id")
    private Long userId;

    @JsonProperty("user name")
    private String userName;
}