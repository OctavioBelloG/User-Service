package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotBlank(message = "El tipo de notificación es obligatorio")
    @Pattern(regexp = "^(message|reminder|alert)$",
            message = "El tipo debe ser: message, reminder o alert")
    private String type;

    @NotBlank(message = "El mensaje es obligatorio")
    private String message;

    @Pattern(regexp = "^(pending|sent|read)$",
            message = "El estado debe ser: pending, sent o read")
    private String status;
}