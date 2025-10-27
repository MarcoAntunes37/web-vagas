package com.webvagas.api.admin_api.application.service.webhook;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceLineItem.Pricing;
import com.stripe.model.InvoiceLineItemCollection;
import com.stripe.model.InvoiceLineItem;
import com.stripe.model.Product;
import com.stripe.model.Subscription;
import com.stripe.exception.StripeException;
import com.webvagas.api.admin_api.application.service.keycloak.KeycloakAdminService;
import com.webvagas.api.admin_api.application.service.webhook.utils.StripeUtils;
import com.webvagas.api.admin_api.infrastructure.integration.stripe.StripeClient;

public class WebhookServiceTest {
        @Mock
        private KeycloakAdminService keycloakService;

        @Mock
        private StripeClient stripeClient;

        @Mock
        private Event event;

        @Mock
        private EventDataObjectDeserializer deserializer;

        @Mock
        private Invoice invoice;

        @Mock
        private InvoiceLineItem item;

        @Mock
        private InvoiceLineItemCollection lineCollection;

        @Mock
        private Pricing pricing;

        @Mock
        private Pricing.PriceDetails priceDetails;

        @Mock
        private Product product;

        @Mock
        private Subscription subscription;

        @Mock
        private Customer customer;

        private WebhookServiceImpl webhookService;

        @BeforeEach
        void setup() {
                MockitoAnnotations.openMocks(this);
                webhookService = new WebhookServiceImpl(keycloakService, stripeClient);
        }

        @Test
        void shouldAssignPlanRoleWhenInvoicePaymentSucceeded() throws StripeException {
                try (MockedStatic<StripeUtils> mocked = mockStatic(StripeUtils.class)) {
                        when(event.getType()).thenReturn("invoice.payment_succeeded");
                        when(event.getDataObjectDeserializer()).thenReturn(deserializer);
                        when(invoice.getCustomerEmail()).thenReturn("user@test.com");
                        when(invoice.getLines()).thenReturn(lineCollection);
                        when(lineCollection.getData()).thenReturn(List.of(item));
                        when(item.getPricing()).thenReturn(pricing);
                        when(pricing.getPriceDetails()).thenReturn(priceDetails);
                        when(priceDetails.getProduct()).thenReturn("prod_12345");
                        when(product.getName()).thenReturn("Start mensal");
                        when(stripeClient.retrieveProduct("prod_12345")).thenReturn(product);
                        mocked.when(() -> StripeUtils.deserializeStripeObject(any(), eq(Invoice.class)))
                                        .thenReturn(Optional.of(invoice));

                        webhookService.handleEvent(event);

                        verify(keycloakService).assignPlanRole(any());
                }
        }

        @Test
        void shouldRemovePlanRoleWhenSubscriptionDeleted() throws StripeException {                
                try (MockedStatic<StripeUtils> mocked = mockStatic(StripeUtils.class)) {
                        when(event.getType()).thenReturn("customer.subscription.deleted");
                        when(event.getDataObjectDeserializer()).thenReturn(deserializer);
                        when(subscription.getCustomer()).thenReturn("cus_123");
                        when(stripeClient.retrieveCustomer("cus_123")).thenReturn(customer);
                        when(subscription.getId()).thenReturn("sub_123");
                        when(customer.getEmail()).thenReturn("aaa@test.com");
                        
                        mocked.when(() -> StripeUtils.deserializeStripeObject(any(), eq(Subscription.class)))
                                        .thenReturn(Optional.of(subscription));

                        webhookService.handleEvent(event);

                        verify(keycloakService).removePlanRole(any());
                }
        }
}