package com.example.service;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;

import java.util.List;

public interface UserService {
   // List<UserResponse> findAll();
List<UserResponse> getUsersPaged(int page, int pageSize);
    List<UserResponse> getUsersByRolePaged(Long roleId, int page, int pageSize);

    UserResponse findById(Long userId);

    UserResponse create(UserRequest req);

    UserResponse update(Long userId, UserRequest req);

    // Nuevo método para cambiar estado (PUT /users/{id}/status)
    UserResponse changeStatus(Long userId, String newStatus);
}
