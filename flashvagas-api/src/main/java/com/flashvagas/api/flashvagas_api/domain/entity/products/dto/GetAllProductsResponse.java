package com.flashvagas.api.flashvagas_api.domain.entity.products.dto;

import java.util.List;

public record GetAllProductsResponse(
        List<ProductResponse> products) {
}
