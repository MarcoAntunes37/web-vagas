package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.StripeCustomerId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StripeCustomerIdTest {
    @Test
    void shouldNotCreateStripeCustomerIdWithNullValue() {
        assertThatThrownBy(() -> new StripeCustomerId(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("StripeCustomerId cannot be null.");
    }

    @Test
    void shouldNotCreateStripeCustomerIdWithEmptyValue() {
        assertThatThrownBy(() -> new StripeCustomerId(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("StripeCustomerId cannot be empty.");
    }

    @Test
    void shouldCreateStripecustomerId() {
        StripeCustomerId stripeCustomerId = new StripeCustomerId("stripe_customer_id");

        assertThat(stripeCustomerId.getValue()).isEqualTo("stripe_customer_id");
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        StripeCustomerId stripeCustomerId1 = new StripeCustomerId("stripe_customer_id");
        StripeCustomerId stripeCustomerId2 = new StripeCustomerId("stripe_customer_id");

        assertThat(stripeCustomerId1.equals(stripeCustomerId2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        StripeCustomerId stripeCustomerId1 = new StripeCustomerId("stripe_customer_id");
        StripeCustomerId stripeCustomerId2 = new StripeCustomerId("stripe_customer_id2");

        assertThat(stripeCustomerId1.equals(stripeCustomerId2)).isFalse();
    }
}