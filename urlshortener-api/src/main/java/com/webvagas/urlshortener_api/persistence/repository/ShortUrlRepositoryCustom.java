package com.webvagas.urlshortener_api.persistence.repository;

import java.util.List;

import com.webvagas.urlshortener_api.persistence.repository.projections.ShortUrlBatchInsertProjection;

public interface ShortUrlRepositoryCustom {
     List<ShortUrlBatchInsertProjection> insertBatchAndReturn(List<String> codes, List<String> urls);
}
