package com.flashvagas.api.flashvagas_api.application.service.customer_portal.command;

import com.flashvagas.api.flashvagas_api.domain.value_object.CustomerEmail;
import com.flashvagas.api.flashvagas_api.domain.value_object.CustomerName;

public record CreateCustomerPortalSessionCommand(CustomerEmail customerEmail, CustomerName customerName) {
}
