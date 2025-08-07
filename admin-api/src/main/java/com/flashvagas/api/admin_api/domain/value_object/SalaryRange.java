package com.flashvagas.api.admin_api.domain.value_object;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SalaryRange {
    private final Integer salary;
    private final Integer min;
    private final Integer max;
    private final String currency;
    private final String period;

    public SalaryRange(Integer salary, Integer min, Integer max, String currency, String period) {
        this.salary = salary;
        this.min = min;
        this.max = max;
        this.currency = currency;
        this.period = period;
    }

    @JsonCreator
    public static SalaryRange toSalaryRangeJ(
            Integer salary, Integer min, Integer max, String currency, String period) {
        return new SalaryRange(salary, min, max, currency, period);
    }

    public SalaryRange getValue() {
        return new SalaryRange(salary, min, max, currency, period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SalaryRange))
            return false;

        SalaryRange salaryRange = (SalaryRange) o;

        return min.equals(salaryRange.min) &&
                max.equals(salaryRange.max) &&
                salary.equals(salaryRange.salary) &&
                currency.equals(salaryRange.currency) &&
                period.equals(salaryRange.period);
    }

    @Override
    public int hashCode() {
        int result = min.hashCode();
        result = 31 * result + max.hashCode();
        result = 31 * result + salary.hashCode();
        result = 31 * result + currency.hashCode();
        result = 31 * result + period.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SalaryRangeJ{" +
                "salary='" + salary + '\'' +
                ", min='" + min + '\'' +
                ", max='" + max + '\'' +
                ", currency='" + currency + '\'' +
                ", period='" + period + '\'' +
                '}';
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
}