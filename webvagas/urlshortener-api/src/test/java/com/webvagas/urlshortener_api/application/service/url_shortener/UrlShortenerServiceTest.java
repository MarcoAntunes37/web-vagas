package com.webvagas.urlshortener_api.application.service.url_shortener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.webvagas.urlshortener_api.infrastructure.repository.UrlShortenerRepository;
import com.webvagas.urlshortener_api.infrastructure.repository.projections.UrlShortenerBatchInsertProjection;
import com.webvagas.urlshortener_api.persistence.shourt_url.ShortUrlEntity;
import com.webvagas.urlshortener_api.application.mappers.UrlShortenerJpaMapper;
import com.webvagas.urlshortener_api.application.service.url_shortener.command.CreateShortUrlCommand;
import com.webvagas.urlshortener_api.application.service.url_shortener.query.GetOriginalUrlQuery;
import com.webvagas.urlshortener_api.domain.entity.short_url.ShortUrl;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.redirect.RedirectResponse;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenResponse;
import com.webvagas.urlshortener_api.domain.value_objects.Code;
import com.webvagas.urlshortener_api.domain.value_objects.DateTime;
import com.webvagas.urlshortener_api.domain.value_objects.OriginalUrl;
import com.webvagas.urlshortener_api.domain.value_objects.ShortUrlId;
import com.webvagas.urlshortener_api.infrastructure.repository.UrlShortenerCustomRepository;

public class UrlShortenerServiceTest {
    @Mock
    private UrlShortenerRepository repository;

    @Mock
    private UrlShortenerCustomRepository repositoryCustom;

    @Mock
    private UrlShortenerJpaMapper mapper;

    private UrlShortenerServiceImpl urlShortenerService;

    @BeforeEach
    void Setup() {
        MockitoAnnotations.openMocks(this);
        urlShortenerService = new UrlShortenerServiceImpl(
                repository, repositoryCustom, mapper);
        ReflectionTestUtils.setField(
                urlShortenerService, "baseUrl", "http://localhost/");
    }

    @Test
    void shouldShortenUrlBatch() {
        CreateShortUrlCommand command = new CreateShortUrlCommand("https://google.com.br/");

        ShortUrlId shortUrlId = new ShortUrlId(1L);

        Code code = new Code("1234");

        OriginalUrl originalUrl = new OriginalUrl("https://google.com.br/");

        DateTime createdAt = new DateTime(LocalDateTime.now());

        ShortUrl domain = new ShortUrl(shortUrlId, code, originalUrl, createdAt);

        ShortUrlEntity entity = new ShortUrlEntity(
                shortUrlId.getValue(),
                code.getValue(),
                originalUrl.getValue(),
                createdAt.getValue());

        ShortenResponse expectedResponse = new ShortenResponse("http://localhost/1234");

        UrlShortenerBatchInsertProjection projection = mock(UrlShortenerBatchInsertProjection.class);

        when(projection.getCode()).thenReturn("1234");
        when(projection.getOriginalUrl()).thenReturn("https://google.com.br/");
        when(mapper.commandToDomain(command)).thenReturn(domain);
        when(mapper.domainToEntity(domain)).thenReturn(entity);
        when(mapper.domainToResponse(domain, "http://localhost")).thenReturn(expectedResponse);
        when(repositoryCustom.insertBatchAndReturn(anyList(), anyList()))
                .thenReturn(List.of(projection));

        List<UrlShortenerBatchInsertProjection> response = urlShortenerService.shortenUrlBatch(List.of(command));

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getOriginalUrl()).isEqualTo(originalUrl.getValue());
    }

    @Test
    void shouldReturnRedirectResponseWhenUrlFound() {
        GetOriginalUrlQuery query = new GetOriginalUrlQuery("abc123");

        ShortUrlEntity entity = new ShortUrlEntity(
                1L,
                "abc123",
                "https://google.com",
                LocalDateTime.now());

        when(repository.findByCode("abc123")).thenReturn(Optional.of(entity));

        Optional<RedirectResponse> result = urlShortenerService.getOriginalUrl(query);

        assertThat(result).isPresent();
        assertThat(result.get().originalUrl()).isEqualTo("https://google.com");
    }

    @Test
    void shouldReturnEmptyWhenUrlNotFound() {
        when(repository.findByCode("unknown")).thenReturn(Optional.empty());

        Optional<RedirectResponse> result = urlShortenerService.getOriginalUrl(new GetOriginalUrlQuery("unknown"));

        assertThat(result).isEmpty();
    }
}