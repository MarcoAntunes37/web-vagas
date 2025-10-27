package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class Employer {
    private String name;
    private String logo;
    private String website;
    private String linkedin;

    public Employer(String name, String logo, String website, String linkedin) {
        Objects.requireNonNull(name, "Name cannot be null.");

        Objects.requireNonNull(logo, "Logo cannot be null.");

        Objects.requireNonNull(website, "Website cannot be null.");

        Objects.requireNonNull(linkedin, "Linkedin cannot be null.");

        this.name = name;
        this.logo = logo;
        this.website = website;
        this.linkedin = linkedin;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getWebsite() {
        return website;
    }

    public String getLinkedin() {
        return linkedin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Employer))
            return false;

        Employer that = (Employer) o;

        return Objects.equals(that.name, this.name)
                && Objects.equals(that.logo, this.logo)
                && Objects.equals(that.website, this.website)
                && Objects.equals(that.linkedin, this.linkedin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, logo, website, linkedin);
    }
}