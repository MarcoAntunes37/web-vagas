package com.webvagas.api.webvagas_api.application.service.checkout_session.command;

import com.webvagas.api.webvagas_api.domain.value_object.CustomerEmail;
import com.webvagas.api.webvagas_api.domain.value_object.CustomerName;
import com.webvagas.api.webvagas_api.domain.value_object.Price;

public record CreateCheckoutSessionCommand(
        Price price,
        CustomerEmail customerEmail,
        CustomerName customerName) {
}
