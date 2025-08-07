package com.flashvagas.api.flashvagas_api.domain.entity.checkout_session;

import com.flashvagas.api.flashvagas_api.domain.value_object.CustomerEmail;
import com.flashvagas.api.flashvagas_api.domain.value_object.CustomerName;
import com.flashvagas.api.flashvagas_api.domain.value_object.Price;

public class CheckoutSession {
    private Price price;
    private CustomerName customerName;
    private CustomerEmail customerEmail;

    public CheckoutSession(Price price, CustomerName customerName, CustomerEmail customerEmail) {
        this.price = price;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public Price getPrice() {
        return price;
    }

    public CustomerName getCustomerName() {
        return customerName;
    }

    public CustomerEmail getCustomerEmail() {
        return customerEmail;
    }
}