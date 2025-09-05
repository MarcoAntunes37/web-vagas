package com.flashvagas.urlshortener_api.persistence.repository;

import java.util.List;

import com.flashvagas.urlshortener_api.persistence.repository.projections.ShortUrlBatchInsertProjection;

public interface ShortUrlRepositoryCustom {
     List<ShortUrlBatchInsertProjection> insertBatchAndReturn(List<String> codes, List<String> urls);
}
