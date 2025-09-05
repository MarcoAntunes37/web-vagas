package com.flashvagas.urlshortener_api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flashvagas.urlshortener_api.infrastructure.entity.ShortUrlEntity;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
        Optional<ShortUrlEntity> findByCode(String code);
}