package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;
@Value
@Builder
public class UserResponse {
  Long userId;
  @JsonProperty
    String userName;
    String email;
    String roleName;
    String status;
}
