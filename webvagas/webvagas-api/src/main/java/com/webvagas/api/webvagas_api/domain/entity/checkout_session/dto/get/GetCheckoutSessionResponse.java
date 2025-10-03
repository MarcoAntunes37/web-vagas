package com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.get;

import java.math.BigDecimal;

public record GetCheckoutSessionResponse(
        String productName,
        BigDecimal price,
        String date) {
}
