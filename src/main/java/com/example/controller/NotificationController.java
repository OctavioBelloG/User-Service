package com.example.controller;

import com.example.dto.NotificationRequest;
import com.example.dto.NotificationResponse;
import com.example.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Notifications", description = "Provides methods for managing user notifications")
public class NotificationController {

    private final NotificationService service;

    /**
     * Consultar notificaciones de usuario
     * GET /notifications/user/{id}?page=0&pageSize=10
     */
    @Operation(summary = "Get notifications by user ID")
    @GetMapping(value = "/user/{id}", params = {"page", "pageSize"})
    public ResponseEntity<List<NotificationResponse>> findByUserId(
            @PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<NotificationResponse> notifications = service.findByUserId(id, page, pageSize);
        return new ResponseEntity<>(notifications, HttpStatus.OK); // 200
    }

    /**
     * Crear nueva notificación
     * POST /notifications
     */
    @Operation(summary = "Create a new notification")
    @PostMapping
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody NotificationRequest req) {
        NotificationResponse notification = service.create(req);
        return new ResponseEntity<>(notification, HttpStatus.CREATED); // 201
    }

    /**
     * Marcar como leída o enviada
     * PUT /notifications/{id}/status?status=read
     */
    @Operation(summary = "Update notification status")
    @PutMapping("/{id}/status")
    public ResponseEntity<NotificationResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        NotificationResponse notification = service.updateStatus(id, status);
        return new ResponseEntity<>(notification, HttpStatus.OK); // 200
    }

    /**
     * Notificaciones pendientes (no leídas)
     * GET /notifications/unread/{userId}
     */
    @Operation(summary = "Get unread notifications by user ID")
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<NotificationResponse>> findUnreadByUserId(@PathVariable Long userId) {
        List<NotificationResponse> notifications = service.findUnreadByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK); // 200
    }
}