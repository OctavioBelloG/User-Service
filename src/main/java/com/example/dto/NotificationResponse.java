package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import java.time.LocalDateTime;

@Value
@Builder
public class NotificationResponse {

    @JsonProperty("notification_id")
    Long notificationId;

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("username")
    String username;

    @JsonProperty("type")
    String type;

    @JsonProperty("message")
    String message;

    @JsonProperty("status")
    String status;

    @JsonProperty("send_date")
    LocalDateTime sendDate;
}