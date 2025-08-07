package com.flashvagas.api.admin_api.domain.value_object;

public class UserAttributes {
    private String[] locale;
    private String[] phone;

    public UserAttributes(String[] locale, String[] phone) {
        this.locale = locale;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof UserAttributes))
            return false;

        UserAttributes userAttributes = (UserAttributes) o;

        return locale[0].equals(userAttributes.locale[0])
                && phone[0].equals(userAttributes.phone[0]);
    }

    @Override
    public int hashCode() {
        int result = locale[0].hashCode();

        result = 31 * result + phone[0].hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "UserAttributes{" +
                "locale='" + locale[0] + '\'' +
                ", phone='" + phone[0] + '\'' +
                '}';
    }

    public String[] getLocale() {
        return locale;
    }

    public String[] getPhone() {
        return phone;
    }
}
