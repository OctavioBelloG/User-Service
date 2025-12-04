package com.example.graphql;

import com.example.dto.RoleRequest;
import com.example.dto.RoleResponse;
import com.example.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoleGraphqlController {

    private final RoleService roleService;

    @QueryMapping
    @PreAuthorize("hasRole('Admin')") // Ajusta el rol según tu necesidad
    public RoleResponse getRoleById(@Argument Long id) {
        return roleService.getRoleById(id);
    }

    @QueryMapping
    @PreAuthorize("hasRole('Admin')")
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles();
    }

    @MutationMapping
    @PreAuthorize("hasRole('Admin')")
    public RoleResponse createRole(@Valid @Argument RoleRequest req) {
        return roleService.createRole(req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('Admin')")
    public RoleResponse updateRole(@Argument Long id, @Valid @Argument RoleRequest req) {
        return roleService.updateRole(id, req);
    }
}