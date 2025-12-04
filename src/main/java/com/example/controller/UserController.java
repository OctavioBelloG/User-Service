package com.example.controller;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation; // Importar
import io.swagger.v3.oas.annotations.tags.Tag; // Importar
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@Tag(name = "Users", description = "Provides methods for managing user accounts")
public class UserController {

    private final UserService service;

    // RUTA: GET /users/pagination?page=&size=
    @Operation(summary = "Listado paginado de usuarios")
    @GetMapping(value = "/pagination", params = {"page", "size"})
    public ResponseEntity<List<UserResponse>> getUsersPaged(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<UserResponse> users = service.getUsersPaged(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // RUTA: GET /users/role/{roleId}?page=&size=
    @Operation(summary = "Usuarios por rol (paginado)")
    @GetMapping(value = "/role/{roleId}", params = {"page", "size"})
    public ResponseEntity<List<UserResponse>> getUsersByRole(
            @PathVariable Long roleId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<UserResponse> users = service.getUsersByRolePaged(roleId, page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // RUTA: GET /users/{id}
    @Operation(summary = "Ver usuario por ID")
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    // RUTA: POST /users/register
    @Operation(summary = "Registrar usuario")
    @PostMapping("/register") // Cambiado a /register
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest req) {
        UserResponse created = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + created.getUserId()))
                .body(created);
    }

    // RUTA: PUT /users/{id}
    @Operation(summary = "Actualizar usuario")
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable("id") Long id, @Valid @RequestBody UserRequest req) {
        return service.update(id, req);
    }

    // RUTA: PUT /users/{id}/status
    @Operation(summary = "Activar/desactivar usuario")
    @PutMapping("/{id}/status")
    public UserResponse changeStatus(
            @PathVariable Long id,
            @RequestParam("status") String newStatus) { // Espera ?status=ACTIVE o ?status=INACTIVE
        return service.changeStatus(id, newStatus);
    }
}