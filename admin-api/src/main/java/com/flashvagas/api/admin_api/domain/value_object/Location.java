package com.flashvagas.api.admin_api.domain.value_object;

public class Location {
    private String region;
    private String city;
    private String state;
    private String country;
    private double latitude;
    private double longitude;

    public Location(String region, String city, String state, String country, double latitude, double longitude) {
        this.region = region;
        this.city = city;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Location))
            return false;

        Location location = (Location) o;

        return region.equals(location.region) &&
                city.equals(location.city) &&
                state.equals(location.state) &&
                country.equals(location.country) &&
                latitude == location.latitude &&
                longitude == location.longitude;
    }

    @Override
    public int hashCode() {
        int result = region.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + Double.hashCode(latitude);
        result = 31 * result + Double.hashCode(longitude);
        return result;
    }

    @Override
    public String toString() {
        return "LocationJ{" +
                "region='" + region + '\'' +
                ", city=" + city +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
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
    
}
