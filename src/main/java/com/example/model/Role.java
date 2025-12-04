package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "ROLES")
public class Role {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_role_id")
    private Long roleId;

    @Column(name = "role_name", unique = true, nullable = false) // Añadir unique y nullable
    private String roleName;

    @Column(name = "permissions", columnDefinition = "TEXT") // Mapeo de tipo TEXT
    private String permissions;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
}