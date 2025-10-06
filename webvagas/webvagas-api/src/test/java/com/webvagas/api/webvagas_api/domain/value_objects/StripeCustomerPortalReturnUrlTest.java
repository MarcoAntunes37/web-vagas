package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.StripeCustomerPortalReturnUrl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StripeCustomerPortalReturnUrlTest {

    @Test
    void shouldNotCreateStripeCustomerPortalReturnUrlWithNullValue() {
        assertThatThrownBy(() -> new StripeCustomerPortalReturnUrl(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("StripeCustomerPortalReturnUrl cannot be null.");
    }

    @Test
    void shouldNotCreateStripeCustomerPortalReturnUrlWithEmptyValue() {
        assertThatThrownBy(() -> new StripeCustomerPortalReturnUrl(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("StripeCustomerPortalReturnUrl cannot be empty.");
    }

    @Test
    void shouldNotCreateStripeCustomerPortalReturnUrlWithInvalidValue() {
        assertThatThrownBy(() -> new StripeCustomerPortalReturnUrl("invalid-url"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("StripeCustomerPortalReturnUrl must start with https://.");
    }

    @Test
    void shouldCreateStripeCustomerPortalReturnUrl() {
        String returnUrl = "https://example.com";

        StripeCustomerPortalReturnUrl stripeCustomerPortalReturnUrl = new StripeCustomerPortalReturnUrl(
                returnUrl);

        assertThat(stripeCustomerPortalReturnUrl.getValue()).isEqualTo(returnUrl);
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        String returnUrl = "https://example.com";

        StripeCustomerPortalReturnUrl stripeCustomerPortalReturnUrl1 = new StripeCustomerPortalReturnUrl(
                returnUrl);

        StripeCustomerPortalReturnUrl stripeCustomerPortalReturnUrl2 = new StripeCustomerPortalReturnUrl(
                returnUrl);

        assertThat(stripeCustomerPortalReturnUrl1.equals(stripeCustomerPortalReturnUrl2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        String returnUrl1 = "https://example.com";

        String returnUrl2 = "https://example2.com";

        StripeCustomerPortalReturnUrl stripeCustomerPortalReturnUrl1 = new StripeCustomerPortalReturnUrl(
                returnUrl1);
                
        StripeCustomerPortalReturnUrl stripeCustomerPortalReturnUrl2 = new StripeCustomerPortalReturnUrl(
                returnUrl2);

        assertThat(stripeCustomerPortalReturnUrl1.equals(stripeCustomerPortalReturnUrl2)).isFalse();
    }
}
