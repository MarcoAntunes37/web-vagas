package com.webvagas.api.webvagas_api.domain.entity.checkout_session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.CustomerEmail;
import com.webvagas.api.webvagas_api.domain.value_object.CustomerName;
import com.webvagas.api.webvagas_api.domain.value_object.Price;

import org.junit.jupiter.api.BeforeEach;

public class CheckoutSessionTest {
    private Price price;
    private CustomerName customerName;
    private CustomerEmail customerEmail;

    @BeforeEach
    void setup() {
        price = new Price("price_10.00");
        customerName = new CustomerName("John Doe");
        customerEmail = new CustomerEmail("nMx9w@example.com");
    }

    @Test
    void createCheckoutSession() {
        CheckoutSession checkoutSession = new CheckoutSession(price, customerName, customerEmail);

        assertThat(checkoutSession.getPrice()).isEqualTo(price);
        assertThat(checkoutSession.getCustomerName()).isEqualTo(customerName);
        assertThat(checkoutSession.getCustomerEmail()).isEqualTo(customerEmail);
    }
}