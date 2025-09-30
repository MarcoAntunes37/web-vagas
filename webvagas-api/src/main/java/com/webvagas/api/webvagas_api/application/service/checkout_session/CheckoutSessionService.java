package com.webvagas.api.webvagas_api.application.service.checkout_session;

import com.stripe.exception.StripeException;
import com.webvagas.api.webvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.webvagas.api.webvagas_api.application.service.checkout_session.query.CheckoutSessionGetQuery;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionResponse;

public interface CheckoutSessionService {
    CreateCheckoutSessionResponse createCheckoutSession(CreateCheckoutSessionCommand command) throws StripeException;

    GetCheckoutSessionResponse getCheckoutSession(CheckoutSessionGetQuery command) throws StripeException;
}
