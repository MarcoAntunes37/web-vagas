package com.flashvagas.api.flashvagas_api.domain.entity.customer_portal;

import com.flashvagas.api.flashvagas_api.domain.value_object.StripeCustomerId;
import com.flashvagas.api.flashvagas_api.domain.value_object.StripeCustomerPortalReturnUrl;

public class CustomerPortalSession {
    private StripeCustomerId customerId;
    private StripeCustomerPortalReturnUrl returnUrl;

    public CustomerPortalSession(StripeCustomerId customerId, StripeCustomerPortalReturnUrl returnUrl) {
        this.customerId = customerId;
        this.returnUrl = returnUrl;
    }

    public String getCustomerId() {
        return customerId.getValue();
    }

    public String getReturnUrl() {
        return returnUrl.getValue();
    }
}