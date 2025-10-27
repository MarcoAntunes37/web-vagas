package com.webvagas.urlshortener_api.infrastructure.repository;

import java.util.List;

import com.webvagas.urlshortener_api.infrastructure.repository.projections.UrlShortenerBatchInsertProjection;


public interface UrlShortenerCustomRepository {
     List<UrlShortenerBatchInsertProjection> insertBatchAndReturn(List<String> codes, List<String> urls);
}
