package com.webvagas.api.admin_api.application.service.webhook.utils;

import java.util.Optional;

import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;

public class StripeUtils {

    private StripeUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> deserializeStripeObject(Event event, Class<T> clazz) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

        return deserializer.getObject()
                .filter(clazz::isInstance)
                .map(obj -> (T) obj);
    }
}
