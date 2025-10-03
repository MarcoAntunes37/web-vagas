package com.webvagas.urlshortener_api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webvagas.urlshortener_api.application.mappers.ShortenerApiMapper;
import com.webvagas.urlshortener_api.application.service.UrlService;
import com.webvagas.urlshortener_api.application.service.command.CreateShortUrlCommand;
import com.webvagas.urlshortener_api.application.service.query.GetOriginalUrlQuery;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.redirect.RedirectResponse;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenRequest;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenResponse;
import com.webvagas.urlshortener_api.persistence.repository.projections.ShortUrlBatchInsertProjection;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UrlController {
    private final UrlService service;

    @Autowired
    private ShortenerApiMapper mapper;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(@RequestBody ShortenRequest request) {
        CreateShortUrlCommand command = mapper.createRequestToCommand(request);

        ShortenResponse response = service.shortenUrl(command);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/shorten/batch")
    public ResponseEntity<List<ShortUrlBatchInsertProjection>> shortenBatch(@RequestBody List<ShortenRequest> request) {
        List<CreateShortUrlCommand> command = mapper.createBatchRequestCommand(request);

        List<ShortUrlBatchInsertProjection> response = service.shortenUrlBatch(command);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<RedirectResponse> redirect(@PathVariable String code) {
        GetOriginalUrlQuery query = mapper.codeToQuery(code);

        Optional<RedirectResponse> response = service.getOriginalUrl(query);

        if (response.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.PERMANENT_REDIRECT)
                    .location(URI.create(
                            response.get().originalUrl()))
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}