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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar
import org.springframework.web.server.ResponseStatusException; // Importar
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    // **NOTA**: Necesitarías un RoleRepository para validar si el roleId existe, omitido aquí.

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
    
    // AHORA LLAMA AL MÉTODO CON EL NOMBRE CORREGIDO
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
        // Establecer el estado inicial si no viene en el Request (usar el default del modelo)
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
        
        // Verificar unicidad del email solo si el email ha cambiado
        if (!existing.getEmail().equals(req.getEmail()) && repository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado por otro usuario.");
        }

        UserMapper.copyToEntity(req, existing);
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