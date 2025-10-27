package com.webvagas.api.webvagas_api.infrastructure.integrations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.webvagas.api.webvagas_api.infrastructure.integrations.stripe.StripeClient;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { StripeClient.class }, properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration," +
                "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
                "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
@AutoConfigureWireMock(port = 0)
class StripeClientIT {

    @Autowired
    private StripeClient stripeClient;

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        Stripe.overrideApiBase("http://localhost:" + wireMockServer.port());
    }

    @AfterEach
    void tearDown() {
        Stripe.overrideApiBase("https://api.stripe.com");
    }

    @Test
    void shouldCreateCustomerWhenNoneExists() throws Exception {
        stubFor(get(urlPathEqualTo("/v1/customers"))
                .willReturn(okJson("""
                            { "object": "list", "data": [] }
                        """)));

        stubFor(post(urlPathEqualTo("/v1/customers"))
                .willReturn(okJson("""
                            { "id": "cus_12345", "email": "test@example.com", "name": "Test User" }
                        """)));

        Customer customer = stripeClient.getOrCreateCustomer("test@example.com", "Test User");

        assertThat(customer.getId()).isEqualTo("cus_12345");
        assertThat(customer.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldReturnExistingCustomerIfFound() throws Exception {
        stubFor(get(urlPathEqualTo("/v1/customers"))
                .willReturn(okJson("""
                            {
                              "object": "list",
                              "data": [ { "id": "cus_existing", "email": "existing@example.com" } ]
                            }
                        """)));

        Customer customer = stripeClient.getOrCreateCustomer("existing@example.com", "Existing User");

        assertThat(customer.getId()).isEqualTo("cus_existing");
        verify(1, postRequestedFor(urlPathEqualTo("/v1/customers")));
    }

    @Test
    void shouldCreateCheckoutSessionSuccessfully() throws Exception {
        stubFor(get(urlPathMatching("/v1/customers/cus_12345"))
                .willReturn(okJson("{\"id\":\"cus_12345\",\"object\":\"customer\"}")));

        stubFor(get(urlPathMatching("/v1/prices/price_abc"))
                .willReturn(okJson("{\"id\":\"price_abc\",\"object\":\"price\",\"product\":\"prod_001\"}")));

        stubFor(get(urlPathMatching("/v1/products/prod_001"))
                .willReturn(okJson("{\"id\":\"prod_001\",\"object\":\"product\",\"name\":\"Plano Pro\"}")));

        stubFor(post(urlPathEqualTo("/v1/checkout/sessions"))
                .willReturn(okJson("""
                            {"id":"cs_test_123","object":"checkout.session"}
                        """)));

        Session session = stripeClient.createCheckoutSession("price_abc", "cus_12345");

        assertThat(session.getId()).isEqualTo("cs_test_123");
        verify(postRequestedFor(urlPathEqualTo("/v1/checkout/sessions")));
    }
}