package com.flashvagas.api.flashvagas_api.api.controller.customer_portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flashvagas.api.flashvagas_api.application.mapper.customer_portal.CustomerPortalSessionApiMapper;
import com.flashvagas.api.flashvagas_api.application.service.customer_portal.CustomerPortalSessionService;
import com.flashvagas.api.flashvagas_api.application.service.customer_portal.command.CreateCustomerPortalSessionCommand;
import com.flashvagas.api.flashvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.customer_portal.dto.CreateCustomerPortalSessionResponse;

@RestController
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
    public ResponseEntity<CreateCustomerPortalSessionResponse> create(CreateCustomerPortalSessionRequest request)
            throws Exception {

        CreateCustomerPortalSessionCommand command = mapper.createPortalSessionRequestToCommand(request);

        CreateCustomerPortalSessionResponse response = customerPortalService.createCustomerPortalSession(command);

        return ResponseEntity.ok(response);
    }
}
