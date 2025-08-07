package com.flashvagas.api.flashvagas_api.api.controller.checkout_session;

import org.springframework.web.bind.annotation.RestController;

import com.flashvagas.api.flashvagas_api.application.mapper.checkout_session.CheckoutSessionApiMapper;
import com.flashvagas.api.flashvagas_api.application.service.checkout_session.CheckoutSessionService;
import com.flashvagas.api.flashvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.stripe.exception.StripeException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@ControllerAdvice
@RequestMapping("/api/v1/checkout-session")
public class CheckoutSessionController {
    private final CheckoutSessionService checkoutSessionService;

    @Autowired
    private CheckoutSessionApiMapper mapper;

    public CheckoutSessionController(
            CheckoutSessionService checkoutSessionService) {
        this.checkoutSessionService = checkoutSessionService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<CreateCheckoutSessionResponse> create(
            @RequestBody @Valid CreateCheckoutSessionRequest request) throws StripeException {
        CreateCheckoutSessionCommand command = mapper.createCheckoutRequestToCommand(request);

        CreateCheckoutSessionResponse response = checkoutSessionService.createCheckoutSession(command);

        return ResponseEntity.ok(response);
    }
}
