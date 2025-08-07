package com.flashvagas.api.flashvagas_api.application.mapper.customer_portal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.api.flashvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.flashvagas.api.flashvagas_api.domain.entity.customer_portal.CustomerPortalSession;
import com.flashvagas.api.flashvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;
import com.stripe.model.billingportal.Session;

@Mapper(componentModel = "spring")
public interface CustomerPortalSessionApiMapper {
    @Mapping(target = "id", expression = "java(new StripeCustomerId(request.customerId()))")
    CreateCustomerPortalSessionCommand createPortalSessionRequestToCommand(CreateCustomerPortalSessionRequest request);

    @Mapping(target = "returnUrl", expression = "java(new StripeCustomerPortalReturnUrl(session.getUrl()))")
    @Mapping(target = "customerId", expression = "java(new StripeCustomerId(session.getCustomer()))")
    CustomerPortalSession sessionToDomain(Session session);

    @Mapping(target = "url", expression = "java(customerPortalSession.getReturnUrl())")
    CreateCustomerPortalSessionResponse domainToResponse(CustomerPortalSession customerPortalSession);
}