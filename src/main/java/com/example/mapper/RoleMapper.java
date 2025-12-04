package com.example.mapper;

import com.example.dto.RoleRequest;
import com.example.dto.RoleResponse;
import com.example.model.Role;

public final class RoleMapper {

    private RoleMapper() {}

    public static RoleResponse toResponse(Role role) {
        if (role == null) return null;
        return RoleResponse.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .permissions(role.getPermissions())
                .build();
    }

    public static Role toEntity(RoleRequest dto) {
        if (dto == null) return null;
        Role role = new Role();
        role.setRoleName(dto.getRoleName());
        role.setPermissions(dto.getPermissions());
        return role;
    }

    public static void copyToEntity(RoleRequest dto, Role entity) {
        if (dto == null || entity == null) return;
        entity.setRoleName(dto.getRoleName());
        entity.setPermissions(dto.getPermissions());
    }
}