package com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create;

public record CreateCheckoutSessionRequest(
        String price,
        String customerEmail,
        String customerName
) {
}