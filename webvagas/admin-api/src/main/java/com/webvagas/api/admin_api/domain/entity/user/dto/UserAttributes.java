package com.webvagas.api.admin_api.domain.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserAttributes(
                @JsonProperty("locale") String[] locale,
                @JsonProperty("phone") String[] phone) {

}