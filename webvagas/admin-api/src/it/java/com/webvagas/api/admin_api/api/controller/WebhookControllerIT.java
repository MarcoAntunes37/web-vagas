package com.webvagas.api.admin_api.api.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceLineItem;
import com.stripe.model.InvoiceLineItem.Pricing;
import com.stripe.model.InvoiceLineItem.Pricing.PriceDetails;
import com.stripe.model.InvoiceLineItemCollection;
import com.stripe.model.Product;
import com.webvagas.api.admin_api.AdminApiApplication;
import com.webvagas.api.admin_api.application.service.keycloak.KeycloakAdminService;
import com.webvagas.api.admin_api.application.service.keycloak.command.AssignPlanRoleCommand;
import com.webvagas.api.admin_api.application.service.webhook.WebhookService;
import com.webvagas.api.admin_api.application.service.webhook.utils.StripeUtils;
import com.webvagas.api.admin_api.infrastructure.integration.stripe.StripeClient;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        AdminApiApplication.class })
class WebhookControllerIT {
    @LocalServerPort
    private Integer port;

    @MockitoBean
    WebhookService webhookService;

    @MockitoBean
    StripeClient stripeClient;

    @MockitoBean
    KeycloakAdminService keycloakService;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldReceiveWebhookForInvoicePaymentSucceded() throws StripeException {
        String payload = """
                    [
                        {
                            "id": "abc123",
                            "type": "invoice.payment.succeeded"
                        }
                    ]
                """;

        Event event = new Event();
        event.setId("abc123");
        event.setType("invoice.payment.succeeded");

        String fakeSigHeader = "fake_sig_header";

        String fakeSecretKey = "fake_secret_key";

        Invoice mockInvoice = mock(Invoice.class);

        InvoiceLineItem lineItem = mock(InvoiceLineItem.class);

        InvoiceLineItemCollection lines = mock(InvoiceLineItemCollection.class);

        PriceDetails priceDetails = mock(PriceDetails.class);

        Pricing pricing = mock(Pricing.class);

        Product product = mock(Product.class);

        when(stripeClient.constructEvent(payload, fakeSigHeader, fakeSecretKey)).thenReturn(event);
        when(mockInvoice.getCustomerEmail()).thenReturn("user@test.com");
        when(mockInvoice.getLines()).thenReturn(lines);
        when(lines.getData()).thenReturn(List.of(lineItem));
        when(lineItem.getPricing()).thenReturn(pricing);
        when(pricing.getPriceDetails()).thenReturn(priceDetails);
        when(priceDetails.getProduct()).thenReturn("prod_123");

        try (MockedStatic<StripeUtils> mocked = mockStatic(StripeUtils.class)) {
            mocked.when(() -> StripeUtils.deserializeStripeObject(event, Invoice.class))
                    .thenReturn(Optional.of(mockInvoice));

            when(stripeClient.retrieveProduct("prod_123"))
                    .thenReturn(product);

            when(product.getName()).thenReturn("FlashVagas Turbo");
            doNothing().when(keycloakService)
                    .assignPlanRole(new AssignPlanRoleCommand("user@test.com", "turbo"));

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .post("/api/v1/webhook")
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }

    @Test
    void shouldReceiveWebhookForCustomerSubscriptionDeleted() throws StripeException {
        String payload = """
                    [
                        {
                            "id": "abc123",
                            "type": "customer.subscription.deleted"
                        }
                    ]
                """;

        Event event = new Event();
        event.setId("abc123");
        event.setType("customer.subscription.deleted");

        String fakeSigHeader = "fake_sig_header";

        String fakeSecretKey = "fake_secret_key";

        Invoice mockInvoice = mock(Invoice.class);

        InvoiceLineItem lineItem = mock(InvoiceLineItem.class);

        InvoiceLineItemCollection lines = mock(InvoiceLineItemCollection.class);

        PriceDetails priceDetails = mock(PriceDetails.class);

        Pricing pricing = mock(Pricing.class);

        Product product = mock(Product.class);

        when(stripeClient.constructEvent(payload, fakeSigHeader, fakeSecretKey)).thenReturn(event);
        when(mockInvoice.getCustomerEmail()).thenReturn("user@test.com");
        when(mockInvoice.getLines()).thenReturn(lines);
        when(lines.getData()).thenReturn(List.of(lineItem));
        when(lineItem.getPricing()).thenReturn(pricing);
        when(pricing.getPriceDetails()).thenReturn(priceDetails);
        when(priceDetails.getProduct()).thenReturn("prod_123");

        try (MockedStatic<StripeUtils> mocked = mockStatic(StripeUtils.class)) {
            mocked.when(() -> StripeUtils.deserializeStripeObject(event, Invoice.class))
                    .thenReturn(Optional.of(mockInvoice));

            when(stripeClient.retrieveProduct("prod_123"))
                    .thenReturn(product);

            when(product.getName()).thenReturn("FlashVagas Turbo");
            doNothing().when(keycloakService)
                    .assignPlanRole(new AssignPlanRoleCommand("user@test.com", "turbo"));

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .post("/api/v1/webhook")
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }
}