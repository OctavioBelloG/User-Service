package com.example.repository;

import com.example.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Buscar notificaciones por user ID
    Page<Notification> findByUser_UserId(Long userId, Pageable pageable);

    // Buscar notificaciones no leídas
    @Query("SELECT n FROM Notification n WHERE n.user.userId = :userId AND n.status != 'read'")
    List<Notification> findUnreadByUserId(@Param("userId") Long userId);
}