package com.webvagas.api.webvagas_api.application.mapper.customer_portal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.stripe.model.billingportal.Session;
import com.webvagas.api.webvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.CustomerPortalSession;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionRequest;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;

@Mapper(componentModel = "spring")
public interface CustomerPortalSessionApiMapper {
    @Mapping(target = "customerEmail", expression = "java(new CustomerEmail(request.customerEmail()))")
    @Mapping(target = "customerName", expression = "java(new CustomerName(request.customerName()))")
    CreateCustomerPortalSessionCommand createPortalSessionRequestToCommand(CreateCustomerPortalSessionRequest request);

    @Mapping(target = "returnUrl", expression = "java(new StripeCustomerPortalReturnUrl(session.getUrl()))")
    @Mapping(target = "customerId", expression = "java(new StripeCustomerId(session.getCustomer()))")
    CustomerPortalSession sessionToDomain(Session session);

    @Mapping(target = "url", expression = "java(customerPortalSession.getReturnUrl().getValue())")
    CreateCustomerPortalSessionResponse domainToResponse(CustomerPortalSession customerPortalSession);
}