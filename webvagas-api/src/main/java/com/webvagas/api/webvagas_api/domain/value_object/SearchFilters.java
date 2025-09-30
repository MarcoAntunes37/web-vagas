package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SearchFilters {
    private Boolean remoteWork;
    private Country country;
    private ExcludeJobPublishers excludeJobPublishers;

    public SearchFilters(Boolean remoteWork, Country country, ExcludeJobPublishers excludeJobPublishers) {
        this.remoteWork = remoteWork;
        this.country = country;
        this.excludeJobPublishers = excludeJobPublishers;
    }

    public Boolean getRemoteWork() {
        return remoteWork;
    }

    public Country getCountry() {
        return country;
    }

    public ExcludeJobPublishers getExcludeJobPublishers() {
        return excludeJobPublishers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SearchFilters))
            return false;
        return Objects.equals(remoteWork, this.remoteWork)
                && Objects.equals(country, this.country)
                && Objects.equals(excludeJobPublishers, this.excludeJobPublishers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remoteWork, country, excludeJobPublishers);
    }

    @JsonCreator
    public static SearchFilters from(Boolean remoteWork, Country country, ExcludeJobPublishers excludeJobPublishers) {
        return new SearchFilters(remoteWork, country, excludeJobPublishers);
    }
}