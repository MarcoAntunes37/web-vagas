package com.webvagas.api.webvagas_api.application.service.customer_portal;

import com.stripe.exception.StripeException;
import com.webvagas.api.webvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;

public interface CustomerPortalSessionService {
    CreateCustomerPortalSessionResponse createCustomerPortalSession(CreateCustomerPortalSessionCommand command) throws StripeException;
}
