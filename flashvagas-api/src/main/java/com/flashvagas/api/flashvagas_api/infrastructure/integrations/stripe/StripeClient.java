package com.flashvagas.api.flashvagas_api.infrastructure.integrations.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.SubscriptionData;
import com.stripe.param.checkout.SessionCreateParams.Mode;
import com.stripe.param.checkout.SessionCreateParams.LineItem;

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

    public Session createCheckoutSession(String priceId, String customerId) throws StripeException {
        Customer customer = retrieveCustomer(customerId);
        if (customer == null) {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setSuccessUrl(domain + "/success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(domain + "/cancel")
                    .setMode(Mode.SUBSCRIPTION)
                    .setSubscriptionData(
                            SubscriptionData.builder()
                                    .setTrialPeriodDays(3L)
                                    .build())
                    .setCustomer(customerId)
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setPrice(priceId)
                            .setQuantity(1L)
                            .build())
                    .build();

            return Session.create(params);
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setSuccessUrl(domain + "/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(domain + "/cancel")
                .setMode(Mode.SUBSCRIPTION)
                .setCustomer(customerId)
                .addLineItem(LineItem.builder()
                        .setPrice(priceId)
                        .setQuantity(1L)
                        .build())
                .build();

        return Session.create(params);
    }

    public Customer retrieveCustomer(String customerId) throws StripeException {
        return Customer.retrieve(customerId);
    }
}