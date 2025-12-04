package com.example.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String street;
    private String intNumber;
    private String extNumber;
    private String neighborhood;
    private String city;
    private String zipCode;
}