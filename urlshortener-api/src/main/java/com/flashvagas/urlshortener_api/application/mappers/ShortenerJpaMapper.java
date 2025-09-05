package com.flashvagas.urlshortener_api.application.mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.urlshortener_api.application.service.command.CreateShortUrlCommand;
import com.flashvagas.urlshortener_api.domain.entity.short_url.ShortUrl;
import com.flashvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenResponse;
import com.flashvagas.urlshortener_api.domain.value_objects.DateTime;
import com.flashvagas.urlshortener_api.infrastructure.entity.ShortUrlEntity;

@Mapper(componentModel = "spring", imports = { LocalDateTime.class, DateTime.class })
public interface ShortenerJpaMapper {
    AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    default String generateCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    default Long generateId() {
        return counter.incrementAndGet();
    }

    default String generateShortUrl(String code, String baseUrl) {
        return baseUrl + code;
    }

    @Mapping(target = "id", expression = "java(new ShortUrlId(generateId()))")
    @Mapping(target = "originalUrl", expression = "java(new OriginalUrl(command.originalUrl()))")
    @Mapping(target = "code", expression = "java(new Code(generateCode()))")
    @Mapping(target = "createdAt", expression = "java(new DateTime(LocalDateTime.now()))")
    ShortUrl commandToDomain(CreateShortUrlCommand command);

    List<ShortUrl> commandListToDomainList(List<CreateShortUrlCommand> command);

    List<ShortUrlEntity> domainListToEntityList(List<ShortUrl> domain);

    ShortUrlEntity domainToEntity(ShortUrl domain);

    @Mapping(target = "shortenedUrl", expression = "java(generateShortUrl(domain.getCode(), baseUrl))")
    ShortenResponse domainToResponse(ShortUrl domain, String baseUrl);
}
