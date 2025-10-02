package com.webvagas.api.webvagas_api.application.service.checkout_session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.webvagas.api.webvagas_api.application.mapper.checkout_session.CheckoutSessionApiMapper;
import com.webvagas.api.webvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.webvagas.api.webvagas_api.application.service.checkout_session.query.CheckoutSessionGetQuery;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.CheckoutSession;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionResponse;
import com.webvagas.api.webvagas_api.infrastructure.integrations.stripe.StripeClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CheckoutSessionServiceImpl implements CheckoutSessionService {
    private final StripeClient stripeClient;

    @Autowired
    private CheckoutSessionApiMapper mapper;

    public CheckoutSessionServiceImpl(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    public CreateCheckoutSessionResponse createCheckoutSession(CreateCheckoutSessionCommand command)
            throws StripeException {
        CheckoutSession checkoutSession = mapper.createCheckoutCommandToDomain(command);

        Customer customer = stripeClient.getOrCreateCustomer(
                checkoutSession.getCustomerEmail().getValue(),
                checkoutSession.getCustomerName().getValue());
        
        log.info("Customer: {}", customer.toJson());

        Session session = stripeClient.createCheckoutSession(
                checkoutSession.getPrice().getValue(), customer.getId());

        log.info("Session: {}", session.toJson());

        CreateCheckoutSessionResponse response = mapper.sessionToCreateResponse(session);

        log.info("Response: {}", response);

        return response;
    }

    public GetCheckoutSessionResponse getCheckoutSession(CheckoutSessionGetQuery query) throws StripeException {
        log.info("Query: {}", query);

        Session session = stripeClient.retrieveSession(query.sessionId());

        log.info("Session: {}", session.toJson());
        
        GetCheckoutSessionResponse response = mapper.sessionToGetResponse(session);

        return response;
    }
}
