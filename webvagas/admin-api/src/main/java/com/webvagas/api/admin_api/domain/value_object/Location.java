package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class Location {
    private String region;
    private String city;
    private String state;
    private String country;
    private double latitude;
    private double longitude;

    public Location(String region, String city, String state, String country, double latitude, double longitude) {
        Objects.requireNonNull(region, "Region cannot be null.");

        Objects.requireNonNull(city, "City cannot be null.");

        Objects.requireNonNull(state, "State cannot be null.");

        Objects.requireNonNull(country, "Country cannot be null.");

        this.region = region;
        this.city = city;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Location))
            return false;

        Location that = (Location) o;

        return Objects.equals(that.region, this.region)
                && Objects.equals(that.city, this.city)
                && Objects.equals(that.state, this.state)
                && Objects.equals(that.country, this.country)
                && Objects.equals(that.latitude, this.latitude)
                && Objects.equals(that.longitude, this.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, city, state, country, latitude, longitude);
    }
}