package com.webvagas.api.webvagas_api.application.mapper.checkout_session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;

import com.stripe.model.checkout.Session;
import com.webvagas.api.webvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionRequest;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionResponse;

public class CheckoutSessionApiMapperTest {
    private CheckoutSessionApiMapper mapper;

    @BeforeEach
    void Setup() {
        mapper = Mappers.getMapper(CheckoutSessionApiMapper.class);
    }

    @Test
    void shouldMapCheckoutSessionCreateCheckoutRequestToCommandCorrectly() {
        String price = "price_1234";
        String email = "azxcv@azxcv.com";
        String name = "testName";

        CreateCheckoutSessionRequest request = new CreateCheckoutSessionRequest(price, email, name);

        CreateCheckoutSessionCommand command = mapper.createCheckoutRequestToCommand(request);

        assertThat(command).isNotNull();
        assertThat(command.price().getValue()).isEqualTo(price);
        assertThat(command.customerEmail().getValue()).isEqualTo(email);
        assertThat(command.customerName().getValue()).isEqualTo(name);
    }

    @Test
    void shouldMapCheckoutSessionSessionToGetResponseCorrectly() {
        String price = "price_1234";

        String returnUrl = "https://example.com.br";

        Session session = new Session();

        session.setCustomer("cus_123");

        session.setUrl(returnUrl);

        GetCheckoutSessionResponse response = mapper.sessionToGetResponse(session);

        assertThat(response).isNotNull();
        assertThat(response.price()).isEqualTo(price);
    }
}
