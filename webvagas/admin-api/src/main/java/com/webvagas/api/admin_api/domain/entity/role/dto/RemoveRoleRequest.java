package com.webvagas.api.admin_api.domain.entity.role.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RemoveRoleRequest(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("composite") boolean composite,
                @JsonProperty("clientRole") boolean clientRole,
                @JsonProperty("containerId") String containerId) {

}
