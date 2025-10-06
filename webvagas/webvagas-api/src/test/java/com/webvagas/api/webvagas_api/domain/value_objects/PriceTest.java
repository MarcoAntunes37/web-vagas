package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.Price;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

public class PriceTest {

    @Test
    void shouldNotCreatePriceWithNullValue() {
        assertThatThrownBy(() -> new Price(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Price cannot be null.");
    }

    @Test
    void shouldNotCreatePriceWithEmptyValue() {
        assertThatThrownBy(() -> new Price(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Price cannot be empty.");
    }

    @Test
    void shouldNotCreatePriceWithInvalidValue() {
        assertThatThrownBy(() -> new Price("invalid_price"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Price must start with price_.");
    }

    @Test
    void shouldCreatePrice() {
        Price price = new Price("price_123");

        assertThat(price.getValue()).isEqualTo("price_123");
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Price price1 = new Price("price_123");
        Price price2 = new Price("price_123");

        assertThat(price1.equals(price2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Price price1 = new Price("price_123");
        Price price2 = new Price("price_456");

        assertThat(price1.equals(price2)).isFalse();
    }
}