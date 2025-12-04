package com.example.controller;

import com.example.dto.LocationRequest;
import com.example.dto.LocationResponse;
import com.example.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Locations", description = "Provides methods for managing patient locations")
public class LocationController {

    private final LocationService service;

    /**
     * Ver dirección del paciente
     * GET /locations/patient/{id}
     */
    @Operation(summary = "Get location by patient ID")
    @GetMapping("/patient/{id}")
    public ResponseEntity<LocationResponse> findByPatientId(@PathVariable Long id) {
        LocationResponse location = service.findByPatientId(id);
        return new ResponseEntity<>(location, HttpStatus.OK); // 200
    }

    /**
     * Registrar dirección
     * POST /locations
     */
    @Operation(summary = "Register a new location")
    @PostMapping
    public ResponseEntity<LocationResponse> create(@Valid @RequestBody LocationRequest req) {
        LocationResponse location = service.create(req);
        return new ResponseEntity<>(location, HttpStatus.CREATED); // 201
    }

    /**
     * Actualizar ubicación
     * PUT /locations/{id}
     */
    @Operation(summary = "Update a location")
    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody LocationRequest req) {
        LocationResponse location = service.update(id, req);
        return new ResponseEntity<>(location, HttpStatus.OK); // 200
    }

    /**
     * Buscar pacientes o médicos cercanos
     * GET /locations/nearby?lat=19.432608&lng=-99.133209&radius=0.1
     */
    @Operation(summary = "Find nearby locations")
    @GetMapping("/nearby")
    public ResponseEntity<List<LocationResponse>> findNearby(
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng,
            @RequestParam(defaultValue = "0.1") BigDecimal radius) {
        List<LocationResponse> locations = service.findNearby(lat, lng, radius);
        return new ResponseEntity<>(locations, HttpStatus.OK); // 200
    }
}