package com.webvagas.api.webvagas_api.application.service.checkout_session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.webvagas.api.webvagas_api.domain.value_object.Price;
import com.stripe.model.checkout.Session;
import com.webvagas.api.webvagas_api.application.mapper.checkout_session.CheckoutSessionApiMapper;
import com.webvagas.api.webvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.webvagas.api.webvagas_api.application.service.checkout_session.query.CheckoutSessionGetQuery;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.CheckoutSession;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionResponse;
import com.webvagas.api.webvagas_api.domain.value_object.CustomerEmail;
import com.webvagas.api.webvagas_api.domain.value_object.CustomerName;
import com.webvagas.api.webvagas_api.infrastructure.integrations.stripe.StripeClient;

public class CheckoutSessionServiceTest {
    @Mock
    private StripeClient stripeClient;
    @Mock
    private CheckoutSessionApiMapper mapper;

    private CheckoutSessionService checkoutService;

    @BeforeEach
    void Setup() {
        MockitoAnnotations.openMocks(this);
        checkoutService = new CheckoutSessionServiceImpl(mapper, stripeClient);
    }

    @Test
    void shouldCreateCheckoutSession() throws StripeException {
        CreateCheckoutSessionCommand command = mock(CreateCheckoutSessionCommand.class);

        CheckoutSession checkoutSession = mock(CheckoutSession.class);

        Customer customer = mock(Customer.class);

        Session session = mock(Session.class);

        CustomerEmail customerEmail = mock(CustomerEmail.class);

        CustomerName customerName = mock(CustomerName.class);

        Price price = mock(Price.class);

        CreateCheckoutSessionResponse expected = mock(CreateCheckoutSessionResponse.class);

        when(mapper.createCheckoutCommandToDomain(command)).thenReturn(checkoutSession);

        when(checkoutSession.getPrice()).thenReturn(price);

        when(checkoutSession.getCustomerName()).thenReturn(customerName);

        when(checkoutSession.getCustomerEmail()).thenReturn(customerEmail);

        when(price.getValue()).thenReturn("price_10.00");

        when(customerEmail.getValue()).thenReturn("nMx9w@example.com");

        when(customerName.getValue()).thenReturn("John Doe");

        when(customer.getId()).thenReturn("cus_1234");

        when(stripeClient.getOrCreateCustomer(
                any(String.class), any(String.class))).thenReturn(customer);

        when(stripeClient.createCheckoutSession(
                any(String.class), any(String.class))).thenReturn(session);

        when(mapper.sessionToCreateResponse(any(Session.class))).thenReturn(expected);

        CreateCheckoutSessionResponse response = checkoutService.createCheckoutSession(command);

        assertThat(response).isNotNull();
    }

    @Test
    void shouldThrowStripeExceptionGetOrCreateCustomerOnCreateCheckoutSession() throws StripeException {
        CreateCheckoutSessionCommand command = mock(CreateCheckoutSessionCommand.class);

        StripeException stripeException = mock(StripeException.class);

        CheckoutSession checkoutSession = mock(CheckoutSession.class);

        CustomerEmail customerEmail = mock(CustomerEmail.class);

        CustomerName customerName = mock(CustomerName.class);

        Price price = mock(Price.class);

        when(checkoutSession.getPrice()).thenReturn(price);

        when(checkoutSession.getCustomerName()).thenReturn(customerName);

        when(checkoutSession.getCustomerEmail()).thenReturn(customerEmail);

        when(price.getValue()).thenReturn("price_10.00");

        when(customerEmail.getValue()).thenReturn("nMx9w@example.com");

        when(customerName.getValue()).thenReturn("John Doe");

        when(mapper.createCheckoutCommandToDomain(command)).thenReturn(checkoutSession);

        when(stripeClient.getOrCreateCustomer(
                any(String.class), any(String.class))).thenThrow(stripeException);

        assertThatThrownBy(() -> checkoutService.createCheckoutSession(command))
                .isInstanceOf(StripeException.class);
    }

    @Test
    void shouldThrowStripeExceptionCreateCheckoutSessionCheckoutSession() throws StripeException {
        CreateCheckoutSessionCommand command = mock(CreateCheckoutSessionCommand.class);

        CheckoutSession checkoutSession = mock(CheckoutSession.class);

        CustomerEmail customerEmail = mock(CustomerEmail.class);

        CustomerName customerName = mock(CustomerName.class);

        Customer customer = mock(Customer.class);

        Price price = mock(Price.class);

        when(checkoutSession.getPrice()).thenReturn(price);

        when(checkoutSession.getCustomerName()).thenReturn(customerName);

        when(checkoutSession.getCustomerEmail()).thenReturn(customerEmail);

        when(price.getValue()).thenReturn("10");

        when(customerEmail.getValue()).thenReturn("nMx9w@example.com");

        when(customerName.getValue()).thenReturn("John Doe");

        when(mapper.createCheckoutCommandToDomain(command)).thenReturn(checkoutSession);

        when(customer.getId()).thenReturn("cus_123");

        when(stripeClient.getOrCreateCustomer(anyString(), anyString())).thenReturn(customer);

        StripeException stripeException = mock(StripeException.class);
        when(stripeException.getMessage()).thenReturn("Simulated Stripe failure");

        when(stripeClient.createCheckoutSession(anyString(), anyString()))
                .thenThrow(stripeException);

        assertThatThrownBy(() -> checkoutService.createCheckoutSession(command))
                .isInstanceOf(StripeException.class)
                .hasMessageContaining("Simulated Stripe failure");

        verify(stripeClient).createCheckoutSession(anyString(), anyString());
    }

    @Test
    void shouldGetCheckoutSession() throws StripeException {
        String sessionId = "session_1234";

        CheckoutSessionGetQuery query = mock(CheckoutSessionGetQuery.class);

        Session session = mock(Session.class);

        GetCheckoutSessionResponse expected = mock(GetCheckoutSessionResponse.class);

        when(query.sessionId()).thenReturn(sessionId);

        when(mapper.sessionToGetResponse(
                any(Session.class))).thenReturn(expected);

        when(stripeClient.retrieveSession(sessionId)).thenReturn(session);

        GetCheckoutSessionResponse result = checkoutService.getCheckoutSession(query);

        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowStripeExceptionGetCheckoutSession() throws StripeException {
        String sessionId = "session_1234";

        CheckoutSessionGetQuery query = mock(CheckoutSessionGetQuery.class);

        when(query.sessionId()).thenReturn(sessionId);

        StripeException stripeException = mock(StripeException.class);

        when(stripeClient.retrieveSession(sessionId)).thenThrow(stripeException);

        assertThatThrownBy(() -> checkoutService.getCheckoutSession(query))
                .isInstanceOf(StripeException.class);
    }
}