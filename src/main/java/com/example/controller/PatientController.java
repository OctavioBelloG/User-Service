package com.example.controller;

import com.example.dto.PatientRequest;
import com.example.dto.PatientResponse;
import com.example.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
@Tag(name = "Patients", description = "Provides methods for managing patients")
public class PatientController {

    private final PatientService service;


    /**
     * Obtiene todos los pacientes con paginación.
     */
    @Operation(summary = "Get all patients with pagination")
    @GetMapping(value = "/paged", params = { "page", "pageSize" })
    public ResponseEntity<List<PatientResponse>> getPatientsPaged(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<PatientResponse> patients = service.getPatientsPaged(page, pageSize);
        return new ResponseEntity<>(patients, HttpStatus.OK); // 200
    }

    /**
     * Busca pacientes por nombre y aplica paginación.
     * Devuelve: 200 OK
     */
    @Operation(summary = "Search patients by name with pagination")
    @GetMapping(value = "/search", params = { "name", "page", "pageSize" })
    public ResponseEntity<List<PatientResponse>> searchPatientsByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<PatientResponse> patients = service.searchPatientsByName(name, page, pageSize);
        return new ResponseEntity<>(patients, HttpStatus.OK); // 200
    }

    /*
    @Operation(summary = "Get all patients")
    @GetMapping
    public ResponseEntity<List<PatientResponse>> findAll() {
        List<PatientResponse> patients = service.findAll();
        return new ResponseEntity<>(paients, HttpStatus.OK); // 200
    }
*/
    /**
     * Devuelve: 200 OK (o 404 NOT FOUND si no existe, manejado en el Service).
     */
    @Operation(summary = "Get a patient by ID")
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponse> findById(@PathVariable Long patientId) {
        PatientResponse patient = service.findById(patientId);
        return new ResponseEntity<>(patient, HttpStatus.OK); // 200
    }


    /**
     * Registra un nuevo paciente.
     * Devuelve: 201 CREATED (o 400 BAD REQUEST por validación/unicidad).
     */
    @Operation(summary = "Register a new patient")
    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientRequest req) {
        PatientResponse savedPatient = service.create(req);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED); // 201
    }

    /**
     * Actualiza completamente los datos de un paciente existente.
     * Devuelve: 200 OK (o 404 NOT FOUND si no existe).
     */
    @Operation(summary = "Update an existing patient")
    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponse> update(@PathVariable Long patientId, @Valid @RequestBody PatientRequest req) {
        PatientResponse updatedPatient = service.update(patientId, req);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK); // 200
    }
}