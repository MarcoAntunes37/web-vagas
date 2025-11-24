package com.webvagas.urlshortener_api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webvagas.urlshortener_api.application.mappers.UrlShortenerApiMapper;
import com.webvagas.urlshortener_api.application.service.url_shortener.UrlShortenerService;
import com.webvagas.urlshortener_api.application.service.url_shortener.command.CreateShortUrlCommand;
import com.webvagas.urlshortener_api.application.service.url_shortener.query.GetOriginalUrlQuery;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.redirect.RedirectResponse;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenRequest;
import com.webvagas.urlshortener_api.domain.entity.short_url.dto.shorten.ShortenResponse;
import com.webvagas.urlshortener_api.infrastructure.repository.projections.UrlShortenerBatchInsertProjection;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Url shortener", description = "Url shortener for webvagas used in message sending")
@RequestMapping
public class UrlShortenerController {
    private final UrlShortenerService service;

    @Autowired
    private UrlShortenerApiMapper mapper;

    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(@RequestBody ShortenRequest request) {
        CreateShortUrlCommand command = mapper.createRequestToCommand(request);

        ShortenResponse response = service.shortenUrl(command);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/shorten/batch")
    public ResponseEntity<List<UrlShortenerBatchInsertProjection>> shortenBatch(
            @RequestBody List<ShortenRequest> request) {
        List<CreateShortUrlCommand> command = mapper.createBatchRequestCommand(request);

        List<UrlShortenerBatchInsertProjection> response = service.shortenUrlBatch(command);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<RedirectResponse> redirect(@PathVariable String code) {
        GetOriginalUrlQuery query = mapper.codeToQuery(code);
        
        Optional<RedirectResponse> response = service.getOriginalUrl(query);

        if (response.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.PERMANENT_REDIRECT)
                    .location(URI.create(response.get().originalUrl()))
                    .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}