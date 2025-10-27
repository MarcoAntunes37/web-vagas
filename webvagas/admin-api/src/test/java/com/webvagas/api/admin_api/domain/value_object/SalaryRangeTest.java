package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class SalaryRangeTest {
    private Integer salary;
    private Integer min;
    private Integer max;
    private String currency;
    private String period;

    @BeforeEach
    void setUp() {
        salary = 1000;
        min = 0;
        max = 1000;
        currency = "USD";
        period = "weekly";
    }

    @Test
    void shouldFailToCreateWhenWhenSalaryIsNull() {
        salary = null;
        assertThatThrownBy(() -> new SalaryRange(salary, min, max, currency, period))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Salary cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenSalaryIsNegative() {
        salary = -1;
        assertThatThrownBy(() -> new SalaryRange(salary, min, max, currency, period))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Salary cannot be negative.");
    }

    @Test
    void shouldFailToCreateWhenWhenMinIsNull() {
        min = null;
        assertThatThrownBy(() -> new SalaryRange(salary, min, max, currency, period))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Min cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenMinIsNegative() {
        min = -1;
        assertThatThrownBy(() -> new SalaryRange(salary, min, max, currency, period))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Min cannot be negative.");
    }

    @Test
    void shouldFailToCreateWhenWhenMaxIsNull() {
        max = null;
        assertThatThrownBy(() -> new SalaryRange(salary, min, max, currency, period))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Max cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenMaxIsLessThanMin() {
        min = 15000;
        assertThatThrownBy(() -> new SalaryRange(salary, min, max, currency, period))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Max cannot be less than min.");
    }

    @Test
    void shouldFailToCreateWhenWhenMaxIsNegative() {
        max = -1;
        assertThatThrownBy(() -> new SalaryRange(salary, min, max, currency, period))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Max cannot be negative.");
    }

    @Test
    void shouldFailToCreateWhenWhenCurrencyIsNull() {
        currency = null;
        assertThatThrownBy(() -> new SalaryRange(salary, min, max, currency, period))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Currency cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenPeriodIsNull() {
        period = null;
        assertThatThrownBy(() -> new SalaryRange(salary, min, max, currency, period))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Period cannot be null.");
    }

    @Test
    void shouldCreateSalaryRange() {
        SalaryRange salaryRange = new SalaryRange(salary, min, max, currency, period);

        assertThat(salaryRange.getValue()).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        SalaryRange salaryRange = new SalaryRange(salary, min, max, currency, period);

        salary = 1000;
        min = 100;
        max = 500;
        currency = "BRL";
        period = "monthly";

        SalaryRange salaryRange2 = new SalaryRange(salary, min, max, currency, period);

        assertThat(salaryRange.equals(salaryRange2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        SalaryRange salaryRange = new SalaryRange(salary, min, max, currency, period);

        SalaryRange salaryRange2 = new SalaryRange(salary, min, max, currency, period);

        assertThat(salaryRange.equals(salaryRange2)).isTrue();
    }
}