package com.webvagas.api.admin_api.domain.entity.role.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AssignRoleRequest(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("composite") Boolean composite,
                @JsonProperty("clientRole") Boolean clientRole,
                @JsonProperty("containerId") String containerId) {

}
