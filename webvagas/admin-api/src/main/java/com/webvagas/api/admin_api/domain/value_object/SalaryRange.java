package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class SalaryRange {
    private final Integer salary;
    private final Integer min;
    private final Integer max;
    private final String currency;
    private final String period;

    public SalaryRange(Integer salary, Integer min, Integer max, String currency, String period) {
        Objects.requireNonNull(salary, "Salary cannot be null.");

        if (salary < 0)
            throw new IllegalArgumentException("Salary cannot be negative.");

        Objects.requireNonNull(min, "Min cannot be null.");

        if (min < 0)
            throw new IllegalArgumentException("Min cannot be negative.");

        Objects.requireNonNull(max, "Max cannot be null.");

        if (max < 0)
            throw new IllegalArgumentException("Max cannot be negative.");

        if (max < min)
            throw new IllegalArgumentException("Max cannot be less than min.");

        Objects.requireNonNull(currency, "Currency cannot be null.");

        Objects.requireNonNull(period, "Period cannot be null.");

        this.salary = salary;
        this.min = min;
        this.max = max;
        this.currency = currency;
        this.period = period;
    }

    public SalaryRange getValue() {
        return new SalaryRange(salary, min, max, currency, period);
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPeriod() {
        return period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SalaryRange))
            return false;

        SalaryRange that = (SalaryRange) o;

        return Objects.equals(that.salary, this.salary)
                && Objects.equals(that.min, this.min)
                && Objects.equals(that.max, this.max)
                && Objects.equals(that.currency, this.currency)
                && Objects.equals(that.period, this.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary, min, max, currency, period);
    }
}