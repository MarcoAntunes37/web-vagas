package com.flashvagas.api.admin_api.domain.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetUserByRoleResponse(
                @JsonProperty("id") String id,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("email") String email,
                @JsonProperty("username") String username,
                @JsonProperty("attributes") Attributes attributes) {
}