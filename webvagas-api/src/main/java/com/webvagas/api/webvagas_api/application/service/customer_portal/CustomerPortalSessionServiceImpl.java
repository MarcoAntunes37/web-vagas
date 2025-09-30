package com.webvagas.api.webvagas_api.application.service.customer_portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.billingportal.Session;
import com.stripe.param.billingportal.SessionCreateParams;
import com.webvagas.api.webvagas_api.application.mapper.customer_portal.CustomerPortalSessionApiMapper;
import com.webvagas.api.webvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.CustomerPortalSession;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;
import com.webvagas.api.webvagas_api.infrastructure.integrations.stripe.StripeClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Component
public class CustomerPortalSessionServiceImpl implements CustomerPortalSessionService {
        @Autowired
        private CustomerPortalSessionApiMapper mapper;

        @Autowired
        private StripeClient stripeClient;

        @Value("${stripe.customer-portal.return-url}")
        private String returnUrl;

        public CreateCustomerPortalSessionResponse createCustomerPortalSession(
                        CreateCustomerPortalSessionCommand command)
                        throws StripeException {
                Customer target = stripeClient
                                .getOrCreateCustomer(command.customerEmail().getValue(),
                                                command.customerName().getValue());

                log.info("Customer: {}", target.toJson());

                SessionCreateParams sessionParams = SessionCreateParams
                                .builder()
                                .setCustomer(target.getId())
                                .setReturnUrl(returnUrl)
                                .build();

                Session session = Session.create(sessionParams);

                CustomerPortalSession customerPortalSession = mapper.sessionToDomain(session);

                log.info("CustomerPortalSession: {}", customerPortalSession);

                CreateCustomerPortalSessionResponse response = mapper.domainToResponse(customerPortalSession);

                return response;
        }
}
