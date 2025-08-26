package com.flashvagas.api.flashvagas_api.application.service.checkout_session;

import com.flashvagas.api.flashvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.flashvagas.api.flashvagas_api.application.service.checkout_session.query.CheckoutSessionGetQuery;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionResponse;
import com.stripe.exception.StripeException;

public interface CheckoutSessionService {
    CreateCheckoutSessionResponse createCheckoutSession(CreateCheckoutSessionCommand command) throws StripeException;

    GetCheckoutSessionResponse getCheckoutSession(CheckoutSessionGetQuery command) throws StripeException;
}
