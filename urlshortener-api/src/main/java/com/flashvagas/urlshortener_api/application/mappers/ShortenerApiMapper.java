package com.flashvagas.urlshortener_api.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.urlshortener_api.application.service.command.CreateShortUrlCommand;
import com.flashvagas.urlshortener_api.application.service.query.GetOriginalUrlQuery;
import com.flashvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenRequest;

@Mapper(componentModel = "spring")
public interface ShortenerApiMapper {

    @Mapping(target = "originalUrl", source = "originalUrl")
    CreateShortUrlCommand createRequestToCommand(ShortenRequest request);

    List<CreateShortUrlCommand> createBatchRequestCommand(List<ShortenRequest> request);

    GetOriginalUrlQuery codeToQuery(String code);
}