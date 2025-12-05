
package com.example.service;

import com.example.dto.RegisterRequest;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Error: El email ya está en uso.");
        }

        // 1. Recuperamos el ID del rol que viene del JSON (request.getRoleId())
        // Si viene nulo, intentamos usar el 1 o el 2 por defecto.
        Long roleIdToSearch = (request.getRoleId() != null) ? request.getRoleId() : 1L; 
        
        // 2. Buscamos por ID, NO por nombre
        Role role = roleRepository.findById(roleIdToSearch)
                .orElseThrow(() -> new RuntimeException("NUEVO ERROR: No existe el Rol con ID: " + roleIdToSearch));

        User user = new User();
        // Mapeamos los datos
        user.setUsername(request.getUsername()); // Ojo: asegúrate que tu DTO usa @JsonProperty("userName") si envías camelCase
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");

        return userRepository.save(user);
    }
}
