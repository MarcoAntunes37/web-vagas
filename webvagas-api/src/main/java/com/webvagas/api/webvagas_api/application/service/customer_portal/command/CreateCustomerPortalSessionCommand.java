package com.webvagas.api.webvagas_api.application.service.customer_portal.command;

import com.webvagas.api.webvagas_api.domain.value_object.CustomerEmail;
import com.webvagas.api.webvagas_api.domain.value_object.CustomerName;

public record CreateCustomerPortalSessionCommand(CustomerEmail customerEmail, CustomerName customerName) {
}
