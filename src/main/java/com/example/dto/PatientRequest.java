package com.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.sql.Date;

@Data
public class PatientRequest {

    // --- Nombres Separados con tus validaciones ---
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "El nombre no debe contener números ni caracteres especiales no permitidos")
    private String firstName;

    @NotBlank(message = "El apellido paterno no puede estar vacío")
    @Size(max = 50, message = "El apellido paterno no debe exceder los 50 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "El apellido paterno no debe contener números ni caracteres especiales")
    private String paternalSurname;

    @Size(max = 50, message = "El apellido materno no debe exceder los 50 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "El apellido materno no debe contener números ni caracteres especiales")
    private String maternalSurname;
    // ----------------------------------------------

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private Date birthDate;

    // IMPORTANTE: @Valid activa las validaciones dentro de AddressDto (los @Size y @NotBlank de arriba)
    @NotNull(message = "La dirección es obligatoria")
    @Valid 
    private AddressDto address;

    @Size(max = 20, message = "El teléfono no debe exceder los 20 caracteres")
    private String phone;

    @NotNull(message = "El usuario es obligatorio")
    private Long userId;
}