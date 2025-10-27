package com.webvagas.urlshortener_api.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webvagas.urlshortener_api.persistence.shourt_url.ShortUrlEntity;

import java.util.Optional;

public interface UrlShortenerRepository extends JpaRepository<ShortUrlEntity, Long> {
        Optional<ShortUrlEntity> findByCode(String code);
}