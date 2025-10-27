package com.webvagas.urlshortener_api.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.webvagas.urlshortener_api.application.service.url_shortener.command.CreateShortUrlCommand;
import com.webvagas.urlshortener_api.application.service.url_shortener.query.GetOriginalUrlQuery;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenRequest;

@Mapper(componentModel = "spring")
public interface UrlShortenerApiMapper {
    CreateShortUrlCommand createRequestToCommand(ShortenRequest request);

    List<CreateShortUrlCommand> createBatchRequestCommand(List<ShortenRequest> request);

    GetOriginalUrlQuery codeToQuery(String code);
}