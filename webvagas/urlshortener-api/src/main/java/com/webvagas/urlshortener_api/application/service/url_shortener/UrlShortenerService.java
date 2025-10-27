package com.webvagas.urlshortener_api.application.service.url_shortener;

import org.springframework.stereotype.Service;

import com.webvagas.urlshortener_api.application.service.url_shortener.command.CreateShortUrlCommand;
import com.webvagas.urlshortener_api.application.service.url_shortener.query.GetOriginalUrlQuery;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.redirect.RedirectResponse;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenResponse;
import com.webvagas.urlshortener_api.infrastructure.repository.projections.UrlShortenerBatchInsertProjection;

import java.util.List;
import java.util.Optional;

@Service
public interface UrlShortenerService {
    ShortenResponse shortenUrl(CreateShortUrlCommand command);

    List<UrlShortenerBatchInsertProjection> shortenUrlBatch(List<CreateShortUrlCommand> command);

    Optional<RedirectResponse> getOriginalUrl(GetOriginalUrlQuery query);
}
