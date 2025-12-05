package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
   @NotBlank
    @JsonProperty("userName") 
    @Schema(example = "Maru", description = "Nombre de usuario")
    private String username;

    @NotBlank
    @Email
    @Schema(example = "maru@gmail.com")
    private String email;

    @NotBlank
    @Schema(example = "123")
    private String password;

    // Agregamos estos campos para que Swagger los muestre y Postman los pueda enviar
    @Schema(example = "1", description = "ID del rol (1 para Admin, 2 para Doctor, etc.)")
    private Long roleId;

    @Schema(example = "ACTIVE", description = "Estado del usuario")
    private String status;

}
