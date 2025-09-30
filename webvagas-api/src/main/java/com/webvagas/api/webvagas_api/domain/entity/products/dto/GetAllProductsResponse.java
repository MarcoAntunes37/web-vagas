package com.webvagas.api.webvagas_api.domain.entity.products.dto;

import java.util.List;

public record GetAllProductsResponse(
        List<ProductResponse> products) {
}
