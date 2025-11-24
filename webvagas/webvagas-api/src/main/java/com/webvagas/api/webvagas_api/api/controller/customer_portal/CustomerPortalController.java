package com.webvagas.api.webvagas_api.api.controller.customer_portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.webvagas.api.webvagas_api.application.mapper.customer_portal.CustomerPortalSessionApiMapper;
import com.webvagas.api.webvagas_api.application.service.customer_portal.CustomerPortalSessionService;
import com.webvagas.api.webvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionRequest;
import com.webvagas.api.webvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Customer portal", description = "Stripe customer portal management")
@ControllerAdvice
@RequestMapping("/api/v1/customer-portal")
public class CustomerPortalController {
    private final CustomerPortalSessionService customerPortalService;

    @Autowired
    private CustomerPortalSessionApiMapper mapper;

    public CustomerPortalController(CustomerPortalSessionService customerPortalService) {
        this.customerPortalService = customerPortalService;
    }

    @PostMapping("/create-portal-session")
    public ResponseEntity<CreateCustomerPortalSessionResponse> create(
        @RequestBody CreateCustomerPortalSessionRequest request)
            throws StripeException {
        CreateCustomerPortalSessionCommand command = mapper.createPortalSessionRequestToCommand(request);

        CreateCustomerPortalSessionResponse response = customerPortalService.createCustomerPortalSession(command);

        return ResponseEntity.ok(response);
    }
}
