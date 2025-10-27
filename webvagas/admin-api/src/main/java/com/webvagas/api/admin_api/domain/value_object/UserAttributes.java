package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class UserAttributes {
    private String[] locale;
    private String[] phone;

    public UserAttributes(String[] locale, String[] phone) {
        Objects.requireNonNull(locale, "Locale cannot be null.");

        Objects.requireNonNull(phone, "Phone cannot be null.");

        if (phone.length == 0)
            throw new IllegalArgumentException("Phone cannot be empty.");

        this.phone = phone;
        this.locale = locale;
    }

    public String[] getLocale() {
        return locale;
    }

    public String[] getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof UserAttributes))
            return false;

        UserAttributes that = (UserAttributes) o;

        return Objects.equals(that.locale, this.locale)
                && Objects.equals(that.phone, this.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, phone);
    }
}
