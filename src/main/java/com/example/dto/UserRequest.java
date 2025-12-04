package com.example.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email; // Importar
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.Data;


@Value
@Builder
@Data
public class UserRequest {
  @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 255, message = "El nombre de usuario no debe exceder los 255 caracteres")
    @JsonProperty("userName")
    String userName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    String email;

    // Solo se requiere en el registro (POST)
    String password;

    @NotNull(message = "El ID del rol es obligatorio")
    Long roleId;

    // Para el PUT /users/{id} y PUT /users/{id}/status
    String status; // ACTIVE, INACTIVE
}
