package com.flashvagas.api.admin_api.domain.entity.role.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flashvagas.api.admin_api.domain.entity.role.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RoleMappingsResponse(
        @JsonProperty("realmMappings") List<Role> realmMappings) {
}