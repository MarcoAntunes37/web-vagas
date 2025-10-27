package com.webvagas.urlshortener_api.application.service.url_shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.webvagas.urlshortener_api.application.mappers.UrlShortenerJpaMapper;
import com.webvagas.urlshortener_api.application.service.url_shortener.command.CreateShortUrlCommand;
import com.webvagas.urlshortener_api.application.service.url_shortener.query.GetOriginalUrlQuery;
import com.webvagas.urlshortener_api.domain.entity.short_url.ShortUrl;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.redirect.RedirectResponse;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenResponse;
import com.webvagas.urlshortener_api.infrastructure.repository.UrlShortenerRepository;
import com.webvagas.urlshortener_api.infrastructure.repository.projections.UrlShortenerBatchInsertProjection;
import com.webvagas.urlshortener_api.infrastructure.repository.UrlShortenerCustomRepository;
import com.webvagas.urlshortener_api.persistence.shourt_url.ShortUrlEntity;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final UrlShortenerRepository repository;
    private final UrlShortenerCustomRepository repositoryCustom;

    @Value("${shortener.url.base-url}")
    private String baseUrl;

    @Autowired
    private UrlShortenerJpaMapper mapper;

    public UrlShortenerServiceImpl(
            UrlShortenerRepository repository,
            UrlShortenerCustomRepository repositoryCustom,
            UrlShortenerJpaMapper mapper) {
        this.repository = repository;
        this.repositoryCustom = repositoryCustom;
        this.mapper = mapper;
    }

    public ShortenResponse shortenUrl(CreateShortUrlCommand command) {
        if (baseUrl == null) {
            throw new IllegalArgumentException("shortener.url.base-url is not set");
        }

        ShortUrl shortUrl = mapper.commandToDomain(command);

        ShortUrlEntity entity = mapper.domainToEntity(shortUrl);

        repository.save(entity);

        ShortenResponse response = mapper.domainToResponse(shortUrl, baseUrl);

        return response;
    }

    @Transactional
    public List<UrlShortenerBatchInsertProjection> shortenUrlBatch(List<CreateShortUrlCommand> command) {
        if (baseUrl == null) {
            throw new IllegalArgumentException("shortener.url.base-url is not set");
        }

        List<ShortUrl> shortUrls = mapper.commandListToDomainList(command);

        List<ShortUrlEntity> entities = mapper.domainListToEntityList(shortUrls);

        return repositoryCustom.insertBatchAndReturn(
                entities.stream().map(ShortUrlEntity::getCode).toList(),
                entities.stream().map(ShortUrlEntity::getOriginalUrl).toList());
    }

    public Optional<RedirectResponse> getOriginalUrl(GetOriginalUrlQuery query) {
        return repository.findByCode(query.code())
                .map(shortUrl -> new RedirectResponse(shortUrl.getOriginalUrl()));
    }
}