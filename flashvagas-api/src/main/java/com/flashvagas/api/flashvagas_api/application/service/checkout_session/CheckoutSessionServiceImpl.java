package com.flashvagas.api.flashvagas_api.application.service.checkout_session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashvagas.api.flashvagas_api.application.mapper.checkout_session.CheckoutSessionApiMapper;
import com.flashvagas.api.flashvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.flashvagas.api.flashvagas_api.application.service.checkout_session.query.CheckoutSessionGetQuery;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.CheckoutSession;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionResponse;
import com.flashvagas.api.flashvagas_api.infrastructure.integrations.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;

@Service
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

        Session session = stripeClient.createCheckoutSession(
                checkoutSession.getPrice().getValue(), customer.getId());

        CreateCheckoutSessionResponse response = mapper.sessionToCreateResponse(session);

        return response;
    }

    public GetCheckoutSessionResponse getCheckoutSession(CheckoutSessionGetQuery query) throws StripeException {
        Session session = stripeClient.retrieveSession(query.sessionId());

        GetCheckoutSessionResponse response = mapper.sessionToGetResponse(session);

        return response;
    }
}
