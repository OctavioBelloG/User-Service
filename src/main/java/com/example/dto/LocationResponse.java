package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;

@Value
@Builder
public class LocationResponse {

    @JsonProperty("location_id")
    Long locationId;

    @JsonProperty("patient_id")
    Long patientId;

    @JsonProperty("patient_name")
    String patientName;

    @JsonProperty("address")
    String address;

    @JsonProperty("latitude")
    BigDecimal latitude;

    @JsonProperty("longitude")
    BigDecimal longitude;
}