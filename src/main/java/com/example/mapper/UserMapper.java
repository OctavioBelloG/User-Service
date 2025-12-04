package com.example.mapper;

import com.example.model.Role;
import com.example.model.User;
import com.example.dto.UserRequest;
import com.example.dto.UserResponse;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .roleName(user.getRole() != null ? user.getRole().getRoleName() : null)
                .status(user.getStatus())
                .build();
    }

    public static User toEntity(UserRequest dto) {
        if (dto == null) return null;
        User user = new User();
        user.setUsername(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setStatus("ACTIVE"); // Valor por defecto
        if (dto.getRoleId() != null) {
            Role role = new Role();
            role.setRoleId(dto.getRoleId());
            user.setRole(role);
        }
        return user;
    }

    public static void copyToEntity(UserRequest dto, User entity) {
        if (dto == null || entity == null) return;
        entity.setUsername(dto.getUserName());
        entity.setEmail(dto.getEmail());
        if (dto.getPassword() != null) entity.setPassword(dto.getPassword());
        if (dto.getRoleId() != null) {
            Role role = new Role();
            role.setRoleId(dto.getRoleId());
            entity.setRole(role);
        }
        entity.setStatus(dto.getStatus());

    }
}
