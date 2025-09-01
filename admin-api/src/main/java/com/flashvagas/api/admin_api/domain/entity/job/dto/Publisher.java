package com.flashvagas.api.admin_api.domain.entity.job.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Publisher(
        @JsonProperty("name") String name) {

}
