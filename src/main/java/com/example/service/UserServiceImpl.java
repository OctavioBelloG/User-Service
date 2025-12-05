package com.example.service;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder; // IMPORTANTE
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder; // INYECCIÓN NECESARIA

    @Override
    public List<UserResponse> getUsersPaged(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<User> users = repository.findAll(pageReq);
        return users.getContent().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getUsersByRolePaged(Long roleId, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        // Asegúrate de que este método exista en tu repositorio
        Page<User> users = repository.findByRoleRoleId(roleId, pageReq);
        return users.getContent().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse findById(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse create(UserRequest req) {
        if (repository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado.");
        }

        User userToSave = UserMapper.toEntity(req);
        
        // CORRECCIÓN CRÍTICA: Encriptar la contraseña antes de guardar
        userToSave.setPassword(passwordEncoder.encode(req.getPassword()));

        if (req.getStatus() == null) {
            userToSave.setStatus("ACTIVE");
        }

        User saved = repository.save(userToSave);
        return UserMapper.toResponse(saved);
    }

    @Override
    public UserResponse update(Long userId, UserRequest req) {
        User existing = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        if (!existing.getEmail().equals(req.getEmail()) && repository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado por otro usuario.");
        }

        // Mapear campos. OJO: Si UserMapper copia la password, asegúrate de encriptarla de nuevo
        // o evita copiar la password en el update si viene vacía.
        UserMapper.copyToEntity(req, existing);
        
        // Opcional: Si el request trae password nueva, encriptarla de nuevo
        if (req.getPassword() != null && !req.getPassword().isEmpty()) {
             existing.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        User saved = repository.save(existing);
        return UserMapper.toResponse(saved);
    }

    @Override
    public UserResponse changeStatus(Long userId, String newStatus) {
        User existing = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        String statusUpper = newStatus.toUpperCase();
        if (!statusUpper.equals("ACTIVE") && !statusUpper.equals("INACTIVE")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado debe ser 'ACTIVE' o 'INACTIVE'.");
        }

        existing.setStatus(statusUpper);
        User saved = repository.save(existing);
        return UserMapper.toResponse(saved);
    }
}
