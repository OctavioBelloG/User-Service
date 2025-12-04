package com.example.controller;

import com.example.dto.MedicalInsuranceRequest;
import com.example.dto.MedicalInsuranceResponse;
import com.example.service.MedicalInsuranceService;
import io.swagger.v3.oas.annotations.Operation; // Importar
import io.swagger.v3.oas.annotations.tags.Tag; // Importar
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // Importar
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // Importar
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/insurances")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@Tag(name = "Medical Insurance", description = "Provides methods for managing medical insurance policies")
public class MedicalInsuranceController {

    private final MedicalInsuranceService service;

    // RUTA: GET /insurance/patient/{id}
    @Operation(summary = "Ver pólizas por ID de paciente")
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<MedicalInsuranceResponse>> getPoliciesByPatientId(@PathVariable Long id) {
        List<MedicalInsuranceResponse> policies = service.getPoliciesByPatientId(id);
        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    // RUTA: GET /insurance/expired
    @Operation(summary = "Ver listado de seguros vencidos")
    @GetMapping("/expired")
    public ResponseEntity<List<MedicalInsuranceResponse>> getExpiredPolicies() {
        List<MedicalInsuranceResponse> policies = service.getExpiredPolicies();
        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    // RUTA: POST /insurance (Registrar)
    @Operation(summary = "Registrar nueva póliza")
    @PostMapping
    public ResponseEntity<MedicalInsuranceResponse> create(@Valid @RequestBody MedicalInsuranceRequest req) {
        MedicalInsuranceResponse created = service.create(req);
        return ResponseEntity.created(URI.create("/api/v1/insurances/" + created.getInsuranceId())).body(created);
    }

    // RUTA: PUT /insurance/{id}
    @Operation(summary = "Actualizar póliza existente")
    @PutMapping("/{id}")
    public MedicalInsuranceResponse update(@PathVariable Long id, @Valid @RequestBody MedicalInsuranceRequest req) {
        return service.update(id, req);
    }
    
    // RUTA: GET /insurances (findAll - Opcional)
    /*
    @GetMapping
    public List<MedicalInsuranceResponse> findAll() {
        return service.findAll();
    }
    */
}