package com.example.mapper;

import com.example.dto.NotificationRequest;
import com.example.dto.NotificationResponse;
import com.example.model.Notification;
import com.example.model.User;

import java.time.LocalDateTime;

public final class NotificationMapper {

    private NotificationMapper() {}

    public static NotificationResponse toResponse(Notification notification) {
        if (notification == null) return null;

        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .userId(notification.getUser() != null ? notification.getUser().getUserId() : null)
                .username(notification.getUser() != null ? notification.getUser().getUsername() : null)
                .type(notification.getType())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .sendDate(notification.getSendDate())
                .build();
    }

    public static Notification toEntity(NotificationRequest dto) {
        if (dto == null) return null;

        Notification notification = new Notification();

        if (dto.getUserId() != null) {
            User user = new User();
            user.setUserId(dto.getUserId());
            notification.setUser(user);
        }

        notification.setType(dto.getType());
        notification.setMessage(dto.getMessage());
        notification.setStatus(dto.getStatus() != null ? dto.getStatus() : "pending");
        notification.setSendDate(LocalDateTime.now());

        return notification;
    }

    public static void copyToEntity(NotificationRequest dto, Notification entity) {
        if (dto == null || entity == null) return;

        if (dto.getUserId() != null) {
            User user = new User();
            user.setUserId(dto.getUserId());
            entity.setUser(user);
        }

        entity.setType(dto.getType());
        entity.setMessage(dto.getMessage());
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }
}