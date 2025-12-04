package com.example.service;


import com.example.dto.RoleRequest;
import com.example.dto.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
    RoleResponse updateRole(Long id, RoleRequest request);
    RoleResponse getRoleById(Long id);
    List<RoleResponse> getAllRoles();
}
