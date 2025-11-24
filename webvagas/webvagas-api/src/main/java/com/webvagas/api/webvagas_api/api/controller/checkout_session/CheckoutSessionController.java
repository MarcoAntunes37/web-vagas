package com.webvagas.api.webvagas_api.api.controller.checkout_session;

import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.webvagas.api.webvagas_api.application.mapper.checkout_session.CheckoutSessionApiMapper;
import com.webvagas.api.webvagas_api.application.service.checkout_session.CheckoutSessionService;
import com.webvagas.api.webvagas_api.application.service.checkout_session.command.CreateCheckoutSessionCommand;
import com.webvagas.api.webvagas_api.application.service.checkout_session.query.CheckoutSessionGetQuery;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionRequest;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.create.CreateCheckoutSessionResponse;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionRequest;
import com.webvagas.api.webvagas_api.domain.entity.checkout_session.dto.get.GetCheckoutSessionResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@ControllerAdvice
@Tag(name = "Checkout session", description = "Stripe checkout session management")
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

    @GetMapping("/get-checkout-session")
    public ResponseEntity<GetCheckoutSessionResponse> get(
            String sessionId) throws StripeException {
                GetCheckoutSessionRequest request = new GetCheckoutSessionRequest(sessionId);
        CheckoutSessionGetQuery command = mapper.getRequestToQuery(request);

        GetCheckoutSessionResponse response = checkoutSessionService.getCheckoutSession(command);

        return ResponseEntity.ok(response);
    }
}
