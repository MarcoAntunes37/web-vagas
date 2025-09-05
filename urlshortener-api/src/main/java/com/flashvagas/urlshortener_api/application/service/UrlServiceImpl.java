package com.flashvagas.urlshortener_api.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.flashvagas.urlshortener_api.application.mappers.ShortenerJpaMapper;
import com.flashvagas.urlshortener_api.application.service.command.CreateShortUrlCommand;
import com.flashvagas.urlshortener_api.application.service.query.GetOriginalUrlQuery;
import com.flashvagas.urlshortener_api.domain.entity.short_url.ShortUrl;
import com.flashvagas.urlshortener_api.domain.entity.short_url.dto.redirect.RedirectResponse;
import com.flashvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenResponse;
import com.flashvagas.urlshortener_api.infrastructure.entity.ShortUrlEntity;
import com.flashvagas.urlshortener_api.persistence.repository.ShortUrlRepository;
import com.flashvagas.urlshortener_api.persistence.repository.ShortUrlRepositoryCustom;
import com.flashvagas.urlshortener_api.persistence.repository.projections.ShortUrlBatchInsertProjection;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class UrlServiceImpl implements UrlService {
    private final ShortUrlRepository repository;
    private final ShortUrlRepositoryCustom repositoryCustom;

    @Value("${shortener.url.base-url}")
    private String baseUrl;

    @Autowired
    private ShortenerJpaMapper mapper;

    public UrlServiceImpl(ShortUrlRepository repository, ShortUrlRepositoryCustom repositoryCustom) {
        this.repository = repository;
        this.repositoryCustom = repositoryCustom;
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
    public List<ShortUrlBatchInsertProjection> shortenUrlBatch(List<CreateShortUrlCommand> command) {
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
