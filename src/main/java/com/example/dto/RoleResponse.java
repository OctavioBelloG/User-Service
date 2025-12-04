package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleResponse {
    @JsonProperty("role_id")
    Long roleId;
    @JsonProperty("role_name")
    String roleName;
    @JsonProperty("permissions")
    String permissions;
}