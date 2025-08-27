package com.flashvagas.api.flashvagas_api.application.service.customer_portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.flashvagas.api.flashvagas_api.application.mapper.customer_portal.CustomerPortalSessionApiMapper;
import com.flashvagas.api.flashvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.flashvagas.api.flashvagas_api.domain.entity.customer_portal.CustomerPortalSession;
import com.flashvagas.api.flashvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;
import com.flashvagas.api.flashvagas_api.infrastructure.integrations.stripe.StripeClient;
import com.stripe.model.Customer;
import com.stripe.model.billingportal.Session;
import com.stripe.param.billingportal.SessionCreateParams;

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
                        throws Exception {

                Customer target = stripeClient
                                .getOrCreateCustomer(command.customerEmail().getValue(),
                                                command.customerName().getValue());

                SessionCreateParams sessionParams = SessionCreateParams
                                .builder()
                                .setCustomer(target.getId())
                                .setReturnUrl(returnUrl)
                                .build();

                Session session = Session.create(sessionParams);

                CustomerPortalSession customerPortalSession = mapper.sessionToDomain(session);

                CreateCustomerPortalSessionResponse response = mapper.domainToResponse(customerPortalSession);

                return response;
        }
}
