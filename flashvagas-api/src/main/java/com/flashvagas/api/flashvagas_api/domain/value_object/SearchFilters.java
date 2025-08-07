package com.flashvagas.api.flashvagas_api.domain.value_object;

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
        SearchFilters searchFilters = (SearchFilters) o;
        return searchFilters.equals(searchFilters);
    }

    @Override
    public int hashCode() {
        int result = remoteWork.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + excludeJobPublishers.hashCode();
        return result;
    }

    @JsonCreator
    public static SearchFilters from(Boolean remoteWork, Country country, ExcludeJobPublishers excludeJobPublishers) {
        return new SearchFilters(remoteWork, country, excludeJobPublishers);
    }
}