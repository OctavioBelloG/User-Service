package com.example.service;

import com.example.dto.NotificationRequest;
import com.example.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> findByUserId(Long userId, int page, int pageSize);

    NotificationResponse create(NotificationRequest req);

    NotificationResponse updateStatus(Long notificationId, String status);

    List<NotificationResponse> findUnreadByUserId(Long userId);
}