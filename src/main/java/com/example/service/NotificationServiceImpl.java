package com.example.service;

import com.example.dto.NotificationRequest;
import com.example.dto.NotificationResponse;
import com.example.mapper.NotificationMapper;
import com.example.model.Notification;
import com.example.model.User;
import com.example.repository.NotificationRepository;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final UserRepository userRepository;

    @Override
    public List<NotificationResponse> findByUserId(Long userId, int page, int pageSize) {
        // Validar que el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + userId);
        }

        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Notification> notifications = repository.findByUser_UserId(userId, pageReq);

        return notifications.getContent().stream()
                .map(notification -> {
                    Hibernate.initialize(notification.getUser());
                    return NotificationMapper.toResponse(notification);
                })
                .toList();
    }

    @Override
    public NotificationResponse create(NotificationRequest req) {
        // Validar que el usuario existe
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + req.getUserId()));

        Notification notificationToSave = NotificationMapper.toEntity(req);
        notificationToSave.setUser(user);

        Notification saved = repository.save(notificationToSave);
        Hibernate.initialize(saved.getUser());

        return NotificationMapper.toResponse(saved);
    }

    @Override
    public NotificationResponse updateStatus(Long notificationId, String status) {
        // Validar el estado
        if (!status.matches("^(pending|sent|read)$")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El estado debe ser: pending, sent o read"
            );
        }

        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Notificación no encontrada con ID: " + notificationId));

        notification.setStatus(status);
        Notification saved = repository.save(notification);

        Hibernate.initialize(saved.getUser());
        return NotificationMapper.toResponse(saved);
    }

    @Override
    public List<NotificationResponse> findUnreadByUserId(Long userId) {
        // Validar que el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + userId);
        }

        return repository.findUnreadByUserId(userId).stream()
                .map(notification -> {
                    Hibernate.initialize(notification.getUser());
                    return NotificationMapper.toResponse(notification);
                })
                .toList();
    }
}