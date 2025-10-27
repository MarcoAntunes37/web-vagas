package com.webvagas.api.webvagas_api.application.mapper.customer_portal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.billingportal.Session;
import com.webvagas.api.webvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.CustomerPortalSession;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionRequest;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;
import com.webvagas.api.webvagas_api.domain.value_object.StripeCustomerId;
import com.webvagas.api.webvagas_api.domain.value_object.StripeCustomerPortalReturnUrl;

public class CustomerPortalSessionApiMapperTest {
    private CustomerPortalSessionApiMapper mapper;

    @BeforeEach
    void Setup() {
        mapper = Mappers.getMapper(CustomerPortalSessionApiMapper.class);
    }

    @Test
    void shouldMapCustomerPortalSessionCreatePortalSessionRequestToCommandCorrectly() {
        String email = "azxcv@azxcv.com";

        String name = "testName";

        CreateCustomerPortalSessionRequest request = new CreateCustomerPortalSessionRequest(email, name);

        CreateCustomerPortalSessionCommand command = mapper.createPortalSessionRequestToCommand(request);

        assertThat(command).isNotNull();
        assertThat(command.customerEmail().getValue()).isEqualTo(email);
        assertThat(command.customerName().getValue()).isEqualTo(name);
    }

    @Test
    void shouldMapCustomerPortalSessionSessionToDomainCorrectly() throws StripeException {
        Customer customer = mock(Customer.class);

        when(customer.getId()).thenReturn("cus_123");

        String returnUrl = "https://example.com.br";

        Session session = new Session();

        session.setCustomer("cus_123");

        session.setUrl(returnUrl);

        CustomerPortalSession domain = mapper.sessionToDomain(session);

        assertThat(domain).isNotNull();
        assertThat(domain.getReturnUrl().getValue()).isEqualTo(returnUrl);
        assertThat(domain.getCustomerId().getValue()).isEqualTo(customer.getId());
    }

    @Test
    void shouldMapCustomerPortalSessionDomainToResponseCorrectly() {
        String returnUrl = "https://example.com.br";

        CustomerPortalSession domain = new CustomerPortalSession(
                new StripeCustomerId("cus_123"), new StripeCustomerPortalReturnUrl(returnUrl));

        CreateCustomerPortalSessionResponse response = mapper.domainToResponse(domain);

        assertThat(response).isNotNull();
        assertThat(response.url()).isEqualTo(returnUrl);
    }
}