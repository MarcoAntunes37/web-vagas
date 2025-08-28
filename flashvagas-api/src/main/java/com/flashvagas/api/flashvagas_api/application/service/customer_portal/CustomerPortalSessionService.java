package com.flashvagas.api.flashvagas_api.application.service.customer_portal;

import com.flashvagas.api.flashvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.flashvagas.api.flashvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;
import com.stripe.exception.StripeException;

public interface CustomerPortalSessionService {
    CreateCustomerPortalSessionResponse createCustomerPortalSession(CreateCustomerPortalSessionCommand command) throws StripeException;
}
