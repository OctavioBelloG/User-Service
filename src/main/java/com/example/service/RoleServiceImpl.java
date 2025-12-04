package com.example.service;

import com.example.dto.RoleRequest;
import com.example.dto.RoleResponse;
import com.example.mapper.RoleMapper;
import com.example.model.Role;
import com.example.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        Role role = RoleMapper.toEntity(request);
        return RoleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));
        RoleMapper.copyToEntity(request, role);
        return RoleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(RoleMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::toResponse)
                .toList();
    }
}