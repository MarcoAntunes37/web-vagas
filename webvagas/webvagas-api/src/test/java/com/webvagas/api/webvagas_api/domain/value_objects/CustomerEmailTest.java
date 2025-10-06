package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.CustomerEmail;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerEmailTest {
    @Test
    void shouldNotCreateCustomerEmailFromNullValue() {
        assertThatThrownBy(() -> new CustomerEmail(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Customer email cannot be null.");
    }

    @Test
    void shouldNotCreateCustomerEmailFromEmptyValue() {
        assertThatThrownBy(() -> new CustomerEmail(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer email cannot be empty.");
    }

    @Test
    void shouldNotCreateCustomerEmailWhenValueDoesNotMatchRegex() {
        assertThatThrownBy(() -> new CustomerEmail("email"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer email must be a valid email address.");
    }

    @Test
    void shouldCreateCustomerEmail() {
        CustomerEmail customerEmail = new CustomerEmail("email@domain.com");

        assertThat(customerEmail.getValue()).isEqualTo("email@domain.com");
    }

    @Test
    void shouldBeEqualWhenAttributesAreTheSame() {
        CustomerEmail customerEmail1 = new CustomerEmail("email@domain.com");
        CustomerEmail customerEmail2 = new CustomerEmail("email@domain.com");

        assertThat(customerEmail1).isEqualTo(customerEmail2);
        assertThat(customerEmail1.hashCode()).isEqualTo(customerEmail2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenAttributesAreDifferent() {
        CustomerEmail customerEmail1 = new CustomerEmail("email@domain.com");
        CustomerEmail customerEmail2 = new CustomerEmail("email2@domain.com");

        assertThat(customerEmail1).isNotEqualTo(customerEmail2);
        assertThat(customerEmail1.hashCode()).isNotEqualTo(customerEmail2.hashCode());
    }
}