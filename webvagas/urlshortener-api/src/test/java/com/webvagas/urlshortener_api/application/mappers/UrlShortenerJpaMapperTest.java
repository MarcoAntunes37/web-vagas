package com.webvagas.urlshortener_api.application.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;

import com.webvagas.urlshortener_api.application.service.url_shortener.command.CreateShortUrlCommand;
import com.webvagas.urlshortener_api.domain.entity.short_url.ShortUrl;

public class UrlShortenerJpaMapperTest {
    private UrlShortenerJpaMapper mapper;

    @BeforeEach
    void Setup() {
        mapper = Mappers.getMapper(UrlShortenerJpaMapper.class);
    }

    @Test
    void shouldMapDomainToResponse() {
        String expectedOriginalUrl = "https://google.com.br/";

        CreateShortUrlCommand command = new CreateShortUrlCommand(expectedOriginalUrl);

        ShortUrl domain = mapper.commandToDomain(command);

        assertThat(domain).isNotNull();
        assertThat(domain.getOriginalUrl()).isEqualTo(expectedOriginalUrl);
    }

    @Test
    void shouldGenerateBase62CodeCorrectly() {
        String encoded = mapper.base62Encode(12345L);

        assertNotNull(encoded);
        assertFalse(encoded.isEmpty());
        assertTrue(encoded.chars().allMatch(c -> UrlShortenerJpaMapper.ALPHABET.indexOf(c) >= 0));
    }

    @Test
    void shouldGenerateRandomStringOfGivenLength() {
        String rand = mapper.randomString(6);

        assertEquals(6, rand.length());
        assertTrue(rand.chars().allMatch(c -> UrlShortenerJpaMapper.ALPHABET.indexOf(c) >= 0));
    }

    @Test
    void shouldGenerateShortUrlFromBaseUrlAndCode() {
        String result = mapper.generateShortUrl("abc123", "https://webvagas.com/");

        assertEquals("https://webvagas.com/abc123", result);
    }

    @Test
    void shouldGenerateCodeFromId() {
        String code = mapper.generateCode(987654321L);

        assertNotNull(code);
        assertTrue(code.length() >= 2);
    }

    @Test
    void shouldConvertCommandListToDomainList() {
        List<CreateShortUrlCommand> commands = List.of(
                new CreateShortUrlCommand("https://google.com"),
                new CreateShortUrlCommand("https://webvagas.com"));

        List<ShortUrl> result = mapper.commandListToDomainList(commands);

        assertEquals(2, result.size());
    }
}