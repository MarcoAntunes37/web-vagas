package com.flashvagas.api.admin_api.domain.value_object;

public class Employer {
    private String name;
    private String logo;
    private String website;
    private String linkedin;

    public Employer(
            String name, String logo, String website, String linkedin) {
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
}
