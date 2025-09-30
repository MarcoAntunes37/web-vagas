package com.webvagas.api.admin_api.domain.entity.job.dto;

public record Parameters(
    String query,
    Integer page,
    Integer numPages,
    String datePosted,
    String country,
    String language
){}
