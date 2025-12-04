package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // 👈 IMPORTADO

import java.util.Collection; // 👈 IMPORTADO
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
public class User implements UserDetails { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    // AÑADIDO: Campo status para activar/desactivar
    @Column(name = "status", nullable = false)
    private String status = "ACTIVE"; // Por defecto, activo

    @ManyToOne(fetch = FetchType.EAGER) // Se recomienda LAZY si no es esencial
    @JoinColumn(name = "fk_role_id", nullable = false)
    private Role role;


    // ======================================================
    // --- MÉTODOS DE USERDETAILS IMPLEMENTADOS ---
    // ======================================================
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    // Estás agregando manualmente "ROLE_"
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
}

    @Override
    public String getUsername() {
        // Usamos el email para el login, como en el artículo
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
      return this.status.equalsIgnoreCase("ACTIVE");
    
    }
    
}