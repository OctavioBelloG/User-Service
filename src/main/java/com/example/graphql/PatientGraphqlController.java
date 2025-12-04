package com.example.graphql;

import com.example.dto.PatientRequest;
import com.example.dto.PatientResponse;
import com.example.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize; // 👈 1. IMPORTACIÓN AÑADIDA
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
// 2. 👈 QUITAR ANOTACIONES DE NIVEL DE CLASE DE AQUÍ
public class PatientGraphqlController {

    private final PatientService service; // Inyección final con Lombok

    // =================================================================
    // --- QUERIES (Consultas) ---
    // =================================================================

    /**
     * Implementación de: getPatientsPaged(page: Int, pageSize: Int)
     * Solo los doctores pueden ver la lista paginada.
     */
    @QueryMapping
    @PreAuthorize("hasRole('Doctor')") // 👈 3. PUESTO AQUÍ
    public List<PatientResponse> getPatientsPaged(@Argument Integer page, @Argument Integer pageSize) {
        int pageNum = (page != null) ? page : 0;
        int size = (pageSize != null) ? pageSize : 10;
        return service.getPatientsPaged(pageNum, size);
    }

    /**
     * Implementación de: searchPatientsByName(name: String!, page: Int, pageSize: Int)
     * Solo los doctores pueden buscar pacientes.
     */
    @QueryMapping
    @PreAuthorize("hasRole('Doctor')") // 👈 3. PUESTO AQUÍ
    public List<PatientResponse> searchPatientsByName(
            @Argument String name,
            @Argument Integer page,
            @Argument Integer pageSize) {

        int pageNum = (page != null) ? page : 0;
        int size = (pageSize != null) ? pageSize : 10;
        return service.searchPatientsByName(name, pageNum, size);
    }

    /**
     * Implementación de: getPatientsWithAppointments(page: Int, pageSize: Int)
     * Solo los doctores pueden ver esta lista especial.
     */
 

    /**
     * Implementación de: findById(patientId: ID!)
     * Un Doctor o un Paciente pueden ver un perfil.
     * (Más adelante, querrás añadir lógica para que un Paciente solo vea el *suyo*).
     */
    @QueryMapping
    @PreAuthorize("hasAnyRole('Doctor', 'Paciente')") // 👈 3. PUESTO AQUÍ
    public PatientResponse findById(@Argument Long patientId) {
        return service.findById(patientId);
    }

    /**
     * Implementación de: findAll
     * Solo para Doctores.
     */
    @QueryMapping
    @PreAuthorize("hasRole('Doctor')") // 👈 3. PUESTO AQUÍ
    public List<PatientResponse> findAll() {
        return service.findAll();
    }

    // =================================================================
    // --- MUTATIONS (Modificaciones) ---
    // =================================================================

    /**
     * Implementación de: create(req: PatientRequest!)
     * Permitido para todos (o para un rol de registro si lo tienes).
     * O si solo un Paciente puede crear su perfil: @PreAuthorize("hasRole('Paciente')")
     */
    @MutationMapping
    @PreAuthorize("permitAll()") // 👈 3. PUESTO AQUÍ (Ejemplo: permitir a todos)
    public PatientResponse create(@Valid @Argument PatientRequest req) {
        return service.create(req);
    }

    /**
     * Implementación de: update(patientId: ID!, req: PatientRequest!)
     * Solo un Paciente puede actualizar su información.
     */
    @MutationMapping
    @PreAuthorize("hasRole('Paciente')") // 👈 3. PUESTO AQUÍ
    public PatientResponse update(@Argument Long patientId, @Valid @Argument PatientRequest req) {
        return service.update(patientId, req);
    }
}