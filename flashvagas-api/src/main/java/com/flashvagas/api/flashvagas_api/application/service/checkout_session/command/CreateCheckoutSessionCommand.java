package com.flashvagas.api.flashvagas_api.application.service.checkout_session.command;

import com.flashvagas.api.flashvagas_api.domain.value_object.CustomerEmail;
import com.flashvagas.api.flashvagas_api.domain.value_object.CustomerName;
import com.flashvagas.api.flashvagas_api.domain.value_object.Price;

public record CreateCheckoutSessionCommand(
        Price price,
        CustomerEmail customerEmail,
        CustomerName customerName) {
}
