package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployerTest {
    private String name;
    private String logo;
    private String website;
    private String linkedin;

    @BeforeEach
    void setUp() {
        name = "name";
        logo = "logo";
        website = "website";
        linkedin = "linkedin";
    }

    @Test
    void shouldFailToCreateWhenWhenNameIsNull() {
        name = null;

        assertThatThrownBy(() -> new Employer(name, logo, website, linkedin))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Name cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenLogoIsNull() {
        logo = null;

        assertThatThrownBy(() -> new Employer(name, logo, website, linkedin))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Logo cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenWebsiteIsNull() {
        website = null;

        assertThatThrownBy(() -> new Employer(name, logo, website, linkedin))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Website cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenLinkedinIsNull() {
        linkedin = null;

        assertThatThrownBy(() -> new Employer(name, logo, website, linkedin))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Linkedin cannot be null.");
    }

    @Test
    void shouldCreateEmployer() {
        Employer employer = new Employer(name, logo, website, linkedin);

        assertThat(employer).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Employer employer = new Employer(name, logo, website, linkedin);

        name = "name2";
        website = "website2";
        linkedin = "linkedin2";
        logo = "logo2";

        Employer employer2 = new Employer(name, logo, website, linkedin);

        assertThat(employer.equals(employer2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Employer employer = new Employer(name, logo, website, linkedin);

        Employer employer2 = new Employer(name, logo, website, linkedin);

        assertThat(employer.equals(employer2)).isTrue();
    }
}