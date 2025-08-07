package com.flashvagas.api.admin_api.infrastructure.integration.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StripeClient {
    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.domain}")
    private String domain;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
        Stripe.setAppInfo("Flash vagas checkout one time payments", "0.0.1", domain);
    }

    public Customer getOrCreateCustomer(String email, String name) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("limit", 1);

        List<Customer> customers = Customer.list(params).getData();

        if (customers.isEmpty()) {
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setEmail(email)
                    .setName(name)
                    .build();

            return Customer.create(customerParams);
        }

        return customers.get(0);
    }

    public Session retrieveSession(String sessionId) throws StripeException {
        return Session.retrieve(sessionId);
    }

    public Event constructEvent(String payload, String sigHeader, String secret) throws StripeException {
        return Webhook.constructEvent(payload, sigHeader, secret);
    }

    public Customer retrieveCustomer(String customerId) throws StripeException {
        return Customer.retrieve(customerId);
    }

    public Price retrievePrice(String priceId) throws StripeException {
        return Price.retrieve(priceId);
    }

    public Product retrieveProduct(String productId) throws StripeException {
        return Product.retrieve(productId);
    }
}