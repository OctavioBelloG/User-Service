package com.example.mapper;

import com.example.dto.LocationRequest;
import com.example.dto.LocationResponse;
import com.example.model.Location;
import com.example.model.Patient;

public final class LocationMapper {

    private LocationMapper() {}

    public static LocationResponse toResponse(Location location) {
        if (location == null) return null;

        return LocationResponse.builder()
                .locationId(location.getLocationId())
                .patientId(location.getPatient() != null ? location.getPatient().getPatientId() : null)
                .patientName(location.getPatient() != null ?
                        location.getPatient().getFirstName() + " " +
                                location.getPatient().getPaternalSurname() : null)
                .address(location.getAddress())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    public static Location toEntity(LocationRequest dto) {
        if (dto == null) return null;

        Location location = new Location();

        if (dto.getPatientId() != null) {
            Patient patient = new Patient();
            patient.setPatientId(dto.getPatientId());
            location.setPatient(patient);
        }

        location.setAddress(dto.getAddress());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());

        return location;
    }

    public static void copyToEntity(LocationRequest dto, Location entity) {
        if (dto == null || entity == null) return;

        if (dto.getPatientId() != null) {
            Patient patient = new Patient();
            patient.setPatientId(dto.getPatientId());
            entity.setPatient(patient);
        }

        entity.setAddress(dto.getAddress());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
    }
}