package com.flashvagas.urlshortener_api.application.service;

import org.springframework.stereotype.Service;

import com.flashvagas.urlshortener_api.application.service.command.CreateShortUrlCommand;
import com.flashvagas.urlshortener_api.application.service.query.GetOriginalUrlQuery;
import com.flashvagas.urlshortener_api.domain.entity.short_url.dto.redirect.RedirectResponse;
import com.flashvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenResponse;
import com.flashvagas.urlshortener_api.persistence.repository.projections.ShortUrlBatchInsertProjection;

import java.util.List;
import java.util.Optional;

@Service
public interface UrlService {
    ShortenResponse shortenUrl(CreateShortUrlCommand command);

    List<ShortUrlBatchInsertProjection> shortenUrlBatch(List<CreateShortUrlCommand> command);

    Optional<RedirectResponse> getOriginalUrl(GetOriginalUrlQuery query);
}
