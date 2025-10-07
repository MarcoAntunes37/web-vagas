package com.webvagas.api.webvagas_api.domain.entity.customer_portal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.StripeCustomerId;
import com.webvagas.api.webvagas_api.domain.value_object.StripeCustomerPortalReturnUrl;

import org.junit.jupiter.api.BeforeEach;

public class CustomerPortalSessionTest {
    private StripeCustomerId customerId;

    private StripeCustomerPortalReturnUrl returnUrl;

    @BeforeEach
    void setup() {
        customerId = new StripeCustomerId(UUID.randomUUID().toString());
        returnUrl = new StripeCustomerPortalReturnUrl("https://example.com");
    }

    @Test
    void shouldCreateCustomerPortalSession() {
        CustomerPortalSession customerPortalSession = new CustomerPortalSession(customerId, returnUrl);

        assertThat(customerPortalSession.getCustomerId()).isEqualTo(customerId);
        assertThat(customerPortalSession.getReturnUrl()).isEqualTo(returnUrl);
    }
}