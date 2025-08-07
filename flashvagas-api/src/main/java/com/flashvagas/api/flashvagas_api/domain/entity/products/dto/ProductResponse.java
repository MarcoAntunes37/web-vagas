package com.flashvagas.api.flashvagas_api.domain.entity.products.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.flashvagas.api.flashvagas_api.domain.value_object.ClientDescriptionItem;

public record ProductResponse(
        String id,
        String name,
        String description,
        Boolean active,
        String defaultPrice,
        Double clientPrice,
        List<ClientDescriptionItem> clientDescriptions,
        OffsetDateTime created,
        OffsetDateTime updated) {
}
