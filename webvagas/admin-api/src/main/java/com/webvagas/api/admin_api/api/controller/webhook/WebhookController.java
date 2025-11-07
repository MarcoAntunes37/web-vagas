package com.webvagas.api.admin_api.api.controller.webhook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webvagas.api.admin_api.application.service.webhook.WebhookService;
import com.webvagas.api.admin_api.infrastructure.integration.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@ControllerAdvice
@RequestMapping
@Slf4j
public class WebhookController {
    private final WebhookService webhookService;
    private final StripeClient stripeClient;

    @Value("${stripe.webhook-secret-key}")
    private String stripeWebhookSecret;

    public WebhookController(
            WebhookService webhookService,
            StripeClient stripeClient) {
        this.webhookService = webhookService;
        this.stripeClient = stripeClient;
    }

    @PostMapping("/api/v1/webhook")
    public ResponseEntity<String> handle(
            @RequestBody String payload,
            HttpServletRequest request) {
        String sigHeader = request.getHeader("Stripe-Signature");
        log.info("Stripe-Signature-Header: {} ", sigHeader);
        try {
            Event event = stripeClient.constructEvent(payload, sigHeader, stripeWebhookSecret);

            webhookService.handleEvent(event);

            return ResponseEntity
                    .ok("Sucesso");
        } catch (StripeException e) {
            log.error("Erro ao processar webhook", e);
            return ResponseEntity
                    .status(400)
                    .body("Erro ao processar webhook");
        }
    }
}