package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleRequest {
   @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
    private String roleName;

    @Size(max = 255, message = "Los permisos no deben exceder los 255 caracteres")
    private String permissions; // Asumiendo que es un String, si es JSON, cambiar anotación
}