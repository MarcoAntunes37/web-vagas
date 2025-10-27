package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAttributesTest {
    private String[] phone;
    private String[] locale;

    @BeforeEach()
    void setUp() {
        List<String> phoneList = List.of("1234567890");

        this.phone = phoneList.toArray(new String[0]);

        List<String> localeList = List.of("en");

        this.locale = localeList.toArray(new String[0]);
    }

    @Test
    void shouldFailToCreateWhenWhenLocaleIsNull() {
        locale = null;

        assertThatThrownBy(() -> new UserAttributes(locale, phone))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Locale cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenPhoneIsNull() {
        phone = null;

        assertThatThrownBy(() -> new UserAttributes(locale, phone))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Phone cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenPhoneIsEmpty() {
        phone = new String[0];

        assertThatThrownBy(() -> new UserAttributes(locale, phone))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Phone cannot be empty.");
    }

    @Test
    void shouldCreateUserAttributes() {
        UserAttributes userAttributes = new UserAttributes(locale, phone);

        assertThat(userAttributes.getPhone()).isEqualTo(phone);
        assertThat(userAttributes.getLocale()).isEqualTo(locale);
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        UserAttributes userAttributes = new UserAttributes(locale, phone);

        phone = new String[] { "1234567891" };

        locale = new String[] { "pt" };

        UserAttributes userAttributes2 = new UserAttributes(locale, phone);

        assertThat(userAttributes.equals(userAttributes2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        UserAttributes userAttributes = new UserAttributes(locale, phone);

        UserAttributes userAttributes2 = new UserAttributes(locale, phone);

        assertThat(userAttributes.equals(userAttributes2)).isTrue();
    }
}