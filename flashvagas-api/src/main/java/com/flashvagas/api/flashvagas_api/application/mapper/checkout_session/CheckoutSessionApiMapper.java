package com.flashvagas.api.flashvagas_api.application.mapper.checkout_session;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.api.flashvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.flashvagas.api.flashvagas_api.application.service.checkout_session.query.CheckoutSessionGetQuery;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.CheckoutSession;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionResponse;
import com.stripe.model.checkout.Session;

@Mapper(componentModel = "spring")
public interface CheckoutSessionApiMapper {

    default BigDecimal toBigDecimal(Long amount) {
        if (amount == null)
            return null;

        return BigDecimal.valueOf(amount)
                .movePointLeft(2);
    }

    default String toDateString(Long epochSeconds) {
        if (epochSeconds == null)
            return null;
            
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZoneId.of("America/Sao_Paulo"));

        return formatter.format(Instant.ofEpochSecond(epochSeconds));
    }

    @Mapping(target = "price", expression = "java(new Price(request.price()))")
    @Mapping(target = "customerName", expression = "java(new CustomerName(request.customerName()))")
    @Mapping(target = "customerEmail", expression = "java(new CustomerEmail(request.customerEmail()))")
    CreateCheckoutSessionCommand createCheckoutRequestToCommand(CreateCheckoutSessionRequest request);

    CheckoutSession createCheckoutCommandToDomain(CreateCheckoutSessionCommand command);

    CreateCheckoutSessionResponse sessionToCreateResponse(Session session);

    CheckoutSessionGetQuery getRequestToQuery(GetCheckoutSessionRequest request);

    @Mapping(target = "productName", expression = "java(session.getMetadata().get(\"productName\"))")
    @Mapping(target = "price", expression = "java(toBigDecimal(session.getAmountTotal()))")
    @Mapping(target = "date", expression = "java(toDateString(session.getCreated()))")
    GetCheckoutSessionResponse sessionToGetResponse(Session session);
}