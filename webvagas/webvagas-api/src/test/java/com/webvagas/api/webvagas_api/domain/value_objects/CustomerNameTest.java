package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.CustomerName;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerNameTest {
    @Test
    void shouldNotCreateCustomerNameFromNullValue() {
        assertThatThrownBy(() -> new CustomerName(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Customer name cannot be null.");
    }

    @Test
    void shouldNotCreateCustomerNameFromEmptyValue() {
        assertThatThrownBy(() -> new CustomerName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer name cannot be empty.");
    }

    @Test
    void shouldCreateCustomerName() {
        CustomerName customerName = new CustomerName("name");

        assertThat(customerName.getValue()).isEqualTo("name");
    }

    @Test
    void shouldBeEqualWhenAttributesAreTheSame() {
        CustomerName customerName1 = new CustomerName("name");
        CustomerName customerName2 = new CustomerName("name");

        assertThat(customerName1).isEqualTo(customerName2);
        assertThat(customerName1.hashCode()).isEqualTo(customerName2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenAttributesAreDifferent() {
        CustomerName customerName1 = new CustomerName("name");
        CustomerName customerName2 = new CustomerName("name2");

        assertThat(customerName1).isNotEqualTo(customerName2);
        assertThat(customerName1.hashCode()).isNotEqualTo(customerName2.hashCode());
    }
}