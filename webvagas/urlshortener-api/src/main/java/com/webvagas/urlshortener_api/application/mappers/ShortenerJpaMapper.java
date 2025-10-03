package com.webvagas.urlshortener_api.application.mappers;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;

import com.webvagas.urlshortener_api.application.service.command.CreateShortUrlCommand;
import com.webvagas.urlshortener_api.domain.entity.short_url.ShortUrl;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenResponse;
import com.webvagas.urlshortener_api.domain.value_objects.Code;
import com.webvagas.urlshortener_api.domain.value_objects.DateTime;
import com.webvagas.urlshortener_api.domain.value_objects.OriginalUrl;
import com.webvagas.urlshortener_api.domain.value_objects.ShortUrlId;
import com.webvagas.urlshortener_api.infrastructure.entity.ShortUrlEntity;

@Mapper(componentModel = "spring", imports = { LocalDateTime.class, DateTime.class })
public interface ShortenerJpaMapper {    
    static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    static final SecureRandom RANDOM = new SecureRandom();

    ShortUrl commandToDomain(CreateShortUrlCommand command);

    List<ShortUrl> commandListToDomainList(List<CreateShortUrlCommand> command);

    List<ShortUrlEntity> domainListToEntityList(List<ShortUrl> domain);
    
    ShortUrlEntity domainToEntity(ShortUrl domain);

    @Mapping(target = "shortenedUrl", expression = "java(generateShortUrl(domain.getCode(), baseUrl))")
    ShortenResponse domainToResponse(ShortUrl domain, String baseUrl);

    @ObjectFactory
    default ShortUrl create(CreateShortUrlCommand command) {
        AtomicLong idSeq = new AtomicLong(System.currentTimeMillis());

        Long id = generateId(idSeq);

        ShortUrl shorturl = new ShortUrl(
                new ShortUrlId(id),
                new Code(generateCode(id)),
                new OriginalUrl(command.originalUrl()),
                new DateTime(LocalDateTime.now()));

        return shorturl;
    }

    @Named("generateCode")
    default String generateCode(Long id) {
        return base62Encode(id) + randomString(2);
    }

    @Named("base62Encode")
    default String base62Encode(Long input) {
        StringBuilder output = new StringBuilder();

        while (input > 0) {
            output.append(ALPHABET.charAt((int) (input % 62)));
            input /= ALPHABET.length();
        }

        return output.reverse().toString();
    }

    default String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return sb.toString();
    }

    default String generateShortUrl(String code, String baseUrl) {
        return baseUrl + code;
    }

    default Long generateId(AtomicLong idSeq) {
        return idSeq.incrementAndGet();
    };
}
