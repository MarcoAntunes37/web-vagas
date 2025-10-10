package com.webvagas.api.webvagas_api.application.service.customer_portal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.billingportal.Session;
import com.stripe.param.billingportal.SessionCreateParams;
import com.webvagas.api.webvagas_api.application.mapper.customer_portal.CustomerPortalSessionApiMapper;
import com.webvagas.api.webvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.CustomerPortalSession;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;
import com.webvagas.api.webvagas_api.domain.value_object.CustomerEmail;
import com.webvagas.api.webvagas_api.domain.value_object.CustomerName;
import com.webvagas.api.webvagas_api.infrastructure.integrations.stripe.StripeClient;

public class CustomerPortalSessionServiceTest {
    @Mock
    private CustomerPortalSessionApiMapper mapper;

    @Mock
    private StripeClient stripeClient;

    private CustomerPortalSessionServiceImpl customerPortalSessionService;

    @BeforeEach
    void Setup() {
        MockitoAnnotations.openMocks(this);
        customerPortalSessionService = new CustomerPortalSessionServiceImpl(mapper, stripeClient);
        ReflectionTestUtils.setField(customerPortalSessionService, "returnUrl", "https://example.com/return");
    }

    @Test
    void shouldCreateCustomerPortalSession() throws StripeException {
        CreateCustomerPortalSessionCommand command = mock(CreateCustomerPortalSessionCommand.class);

        CustomerEmail customerEmail = mock(CustomerEmail.class);

        CustomerName customerName = mock(CustomerName.class);

        when(command.customerEmail()).thenReturn(customerEmail);

        when(command.customerName()).thenReturn(customerName);

        when(customerEmail.getValue()).thenReturn("nMx9w@example.com");

        when(customerName.getValue()).thenReturn("John Doe");

        Customer customer = new Customer();

        customer.setId("cus_123");

        Session session = mock(Session.class);

        CustomerPortalSession domain = mock(CustomerPortalSession.class);

        CreateCustomerPortalSessionResponse response = mock(CreateCustomerPortalSessionResponse.class);

        try (MockedStatic<Session> sessionStatic = mockStatic(Session.class)) {
            when(stripeClient.getOrCreateCustomer("nMx9w@example.com", "John Doe")).thenReturn(customer);

            sessionStatic.when(() -> Session.create(any(SessionCreateParams.class))).thenReturn(session);

            when(mapper.sessionToDomain(session)).thenReturn(domain);

            when(mapper.domainToResponse(domain)).thenReturn(response);

            CreateCustomerPortalSessionResponse result = customerPortalSessionService
                    .createCustomerPortalSession(command);

            assertThat(result).isEqualTo(response);

            verify(stripeClient).getOrCreateCustomer("nMx9w@example.com", "John Doe");

            verify(mapper).sessionToDomain(session);

            verify(mapper).domainToResponse(domain);
        }
    }

    @Test
    void shouldThrowStripeExceptionWhenStripeFails() throws StripeException {
        CreateCustomerPortalSessionCommand command = mock(CreateCustomerPortalSessionCommand.class);

        CustomerEmail customerEmail = mock(CustomerEmail.class);

        CustomerName customerName = mock(CustomerName.class);

        StripeException stripeException = mock(StripeException.class);

        when(stripeException.getMessage()).thenReturn("Stripe error");

        when(command.customerEmail()).thenReturn(customerEmail);

        when(command.customerName()).thenReturn(customerName);

        when(customerEmail.getValue()).thenReturn("fail@example.com");

        when(customerName.getValue()).thenReturn("Fail Name");

        when(stripeClient.getOrCreateCustomer(anyString(), anyString()))
                .thenThrow(stripeException);

        assertThatThrownBy(() -> customerPortalSessionService.createCustomerPortalSession(command))
                .isInstanceOf(StripeException.class)
                .hasMessageContaining("Stripe error");

        verify(stripeClient).getOrCreateCustomer("fail@example.com", "Fail Name");
        verifyNoInteractions(mapper);
    }
}