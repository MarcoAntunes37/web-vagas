package com.flashvagas.api.admin_api.application.service.webhook;

import com.stripe.exception.StripeException;
import com.stripe.model.Event;

public interface WebhookService {
    void handleEvent(Event event) throws StripeException;
}
