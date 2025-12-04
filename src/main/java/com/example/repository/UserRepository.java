package com.example.repository;
import org.springframework.data.domain.Page; // Importar
import org.springframework.data.domain.Pageable; // Importar
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional; 

public interface UserRepository extends JpaRepository<User, Long> {
  
  // --- CAMBIO CLAVE AQUÍ ---
    // Usamos JOIN FETCH para cargar el rol SÍ o SÍ junto con el usuario
    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
    boolean existsByUserId(Long userId);

    Page<User> findAll(Pageable pageable);
    Page<User> findByRoleRoleId(Long roleId, Pageable pageable);
}