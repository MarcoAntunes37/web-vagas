package com.webvagas.api.webvagas_api.infrastructure.intregrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.webvagas.api.webvagas_api.infrastructure.integrations.stripe.StripeClient;

public class StripeClientTest {
    private StripeClient stripeClient;

    @BeforeEach
    void Setup() {
        stripeClient = new StripeClient();
        Stripe.apiKey = "api-key-mock";
        Stripe.setAppInfo("Web vagas checkout one time payments mock", "0.0.1", "domain");
    }

    @Test
    void shouldGetOrCreateCustomer() throws StripeException {
        Customer existingCustomer = new Customer();
        existingCustomer.setId("cus_123");
        existingCustomer.setEmail("test@example.com");
        existingCustomer.setName("John");

        try (MockedStatic<Customer> mockedStatic = mockStatic(Customer.class)) {
            var listResponse = mock(com.stripe.model.CustomerCollection.class);

            when(listResponse.getData()).thenReturn(List.of(existingCustomer));

            mockedStatic.when(() -> Customer.list(anyMap())).thenReturn(listResponse);

            Customer result = stripeClient.getOrCreateCustomer("test@example.com", "John");

            assertThat(result.getId()).isEqualTo("cus_123");
            mockedStatic.verify(() -> Customer.list(anyMap()), times(1));
        }
    }

    @Test
    void shouldCreateCheckoutSession() throws StripeException {
        Customer customer = new Customer();
        customer.setId("cus_123");
        customer.setName("John");
        customer.setEmail("test@example.com");

        Price price = new Price();
        price.setId("price_123");
        price.setProduct("prod_123");

        Product product = new Product();
        product.setId("prod_123");
        product.setName("Product Name");

        try (MockedStatic<Price> mockedStaticPrice = mockStatic(Price.class);
                MockedStatic<Customer> mockedStaticCustomer = mockStatic(Customer.class);
                MockedStatic<Product> mockedStaticProduct = mockStatic(Product.class);
                MockedStatic<Session> mockedStaticSession = mockStatic(Session.class)) {

            mockedStaticPrice.when(() -> Price.retrieve("price_123")).thenReturn(price);

            mockedStaticCustomer.when(() -> Customer.retrieve("cus_123")).thenReturn(customer);

            mockedStaticProduct.when(() -> Product.retrieve("prod_123")).thenReturn(product);

            mockedStaticSession.when(() -> Session.create(any(SessionCreateParams.class))).thenReturn(new Session());

            Session session = stripeClient.createCheckoutSession("price_123", "cus_123");

            assertThat(session).isNotNull();
            mockedStaticSession.verify(() -> Session.create(any(SessionCreateParams.class)), times(1));
        }
    }
}