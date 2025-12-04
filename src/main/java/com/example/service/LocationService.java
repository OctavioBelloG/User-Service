package com.example.service;

import com.example.dto.LocationRequest;
import com.example.dto.LocationResponse;

import java.math.BigDecimal;
import java.util.List;

public interface LocationService {

    LocationResponse findByPatientId(Long patientId);

    LocationResponse create(LocationRequest req);

    LocationResponse update(Long locationId, LocationRequest req);

    List<LocationResponse> findNearby(BigDecimal lat, BigDecimal lng, BigDecimal radius);
}