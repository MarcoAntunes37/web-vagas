package com.webvagas.api.admin_api.domain.entity.job.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Employer(
        @JsonProperty("name") String name,
        @JsonProperty("logo") String logo,
        @JsonProperty("website") String website,
        @JsonProperty("linkedin") String linkedin) {
}
