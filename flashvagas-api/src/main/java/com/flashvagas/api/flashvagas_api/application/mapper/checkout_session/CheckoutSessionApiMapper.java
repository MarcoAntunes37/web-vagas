package com.flashvagas.api.flashvagas_api.application.mapper.checkout_session;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.api.flashvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.CheckoutSession;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.stripe.model.checkout.Session;

@Mapper(componentModel = "spring")
public interface CheckoutSessionApiMapper {
    @Mapping(target = "price", expression = "java(new Price(request.price()))")
    @Mapping(target = "customerName", expression = "java(new CustomerName(request.customerName()))")
    @Mapping(target = "customerEmail", expression = "java(new CustomerEmail(request.customerEmail()))")
    CreateCheckoutSessionCommand createCheckoutRequestToCommand(CreateCheckoutSessionRequest request);

    CheckoutSession createCheckoutCommandToDomain(CreateCheckoutSessionCommand command);

    CreateCheckoutSessionResponse sessionToCreateResponse(Session session);
}