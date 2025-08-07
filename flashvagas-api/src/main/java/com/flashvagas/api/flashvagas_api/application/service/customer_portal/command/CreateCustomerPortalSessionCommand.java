package com.flashvagas.api.flashvagas_api.application.service.customer_portal.command;

import com.flashvagas.api.flashvagas_api.domain.value_object.StripeCustomerId;

public record CreateCustomerPortalSessionCommand(StripeCustomerId id) {
}
