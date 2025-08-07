package com.flashvagas.api.flashvagas_api.application.service.checkout_session;

import com.flashvagas.api.flashvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.stripe.exception.StripeException;

public interface CheckoutSessionService {
    CreateCheckoutSessionResponse createCheckoutSession(CreateCheckoutSessionCommand command) throws StripeException;
}
