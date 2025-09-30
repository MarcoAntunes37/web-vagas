package com.webvagas.api.admin_api.application.service.webhook;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webvagas.api.admin_api.application.service.keycloak.KeycloakAdminService;
import com.webvagas.api.admin_api.application.service.keycloak.command.AssignPlanRoleCommand;
import com.webvagas.api.admin_api.application.service.keycloak.command.RemovePlanRoleCommand;
import com.webvagas.api.admin_api.application.service.webhook.utils.StripeUtils;
import com.webvagas.api.admin_api.infrastructure.integration.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.Product;
import com.stripe.model.Subscription;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebhookServiceImpl implements WebhookService {
    private final KeycloakAdminService keycloakService;
    private final StripeClient stripeClient;

    public WebhookServiceImpl(
            KeycloakAdminService keycloakService,
            StripeClient stripeClient) {
        this.keycloakService = keycloakService;
        this.stripeClient = stripeClient;
    }

    public void handleEvent(Event event) throws StripeException {
        switch (event.getType()) {
            case "checkout.session.completed":
                log.info("checkout.session.completed event received");
                // send confirmation email
                break;

            case "invoice.payment_succeeded":
                log.info("invoice.payment_succeeded event received");
                handleInvoiceSucceeded(event);
                break;

            case "invoice.payment_failed":
                // send information email
                log.info("invoice.payment_failed event received");
                break;

            case "customer.subscription.deleted":
                log.info("customer.subscription.deleted event received");
                handleSubscriptionDeleted(event);
                break;

            default:
                log.info("Unrecognized event type: {}", event.getType());
        }
    }

    private void handleInvoiceSucceeded(Event event) throws StripeException {
        Optional<Invoice> optionalInvoice = StripeUtils.deserializeStripeObject(event, Invoice.class);

        if (optionalInvoice.isEmpty()) {
            log.warn("Evento de pagamento não contém um Invoice válido");

            return;
        }

        Invoice invoice = optionalInvoice.get();

        String email = invoice.getCustomerEmail();

        String productId = invoice.getLines().getData().get(0).getPricing().getPriceDetails().getProduct();

        Product product = stripeClient.retrieveProduct(productId);

        String plan = resolvePlanFromProduct(product);

        if (!plan.isBlank()) {
            log.info("Pagamento bem-sucedido para: {} | Plano: {}", email, plan);

            AssignPlanRoleCommand command = new AssignPlanRoleCommand(email, plan);

            log.info("command: {}", command);

            keycloakService.assignPlanRole(command);

            log.info("Role '{}' adicionada ao usuário com email '{}'", plan, email);
        } else {
            log.warn("Plano inválido para produto: {}", product.getName());
        }
    }

    private void handleSubscriptionDeleted(Event event) {
        StripeUtils.deserializeStripeObject(event, Subscription.class)
                .ifPresentOrElse(subscription -> {
                    try {
                        Customer customer = stripeClient.retrieveCustomer(subscription.getCustomer());

                        String email = customer.getEmail();

                        log.info("Removendo role do usuário com e-mail {} e ID {}", email, customer.getId());

                        RemovePlanRoleCommand command = new RemovePlanRoleCommand(email);

                        keycloakService.removePlanRole(command);
                        log.info("Role removida do usuário com e-mail {}", email);
                    } catch (StripeException e) {
                        throw new RuntimeException("Erro ao buscar cliente Stripe para remoção de role", e);
                    }
                }, () -> log.warn("Objeto do evento não é uma Subscription válida."));
    }

    private String resolvePlanFromProduct(Product productDetails) {
        switch (productDetails.getName()) {
            case "Start mensal":
                return "plan-start";
            case "Start anual":
                return "plan-start";
            case "Turbo mensal":
                return "plan-turbo";
            case "Turbo anual":
                return "plan-turbo";
            default:
                return "";
        }
    }
}