package com.flashvagas.api.admin_api.domain.entity.user_preferences;

import com.flashvagas.api.admin_api.domain.value_object.Country;
import com.flashvagas.api.admin_api.domain.value_object.EmploymentTypes;
import com.flashvagas.api.admin_api.domain.value_object.ExcludeJobPublishers;
import com.flashvagas.api.admin_api.domain.value_object.Keywords;
import com.flashvagas.api.admin_api.domain.value_object.SearchFilters;
import com.flashvagas.api.admin_api.domain.value_object.UserId;

public class UserPreferences {
    private UserId userId;
    private Keywords keywords;
    private EmploymentTypes employmentTypes;
    private SearchFilters searchFilters;

    public UserPreferences(
            UserId userId,
            Keywords keywords,
            EmploymentTypes employmentTypes,
            SearchFilters searchFilters) {
        this.userId = userId;
        this.keywords = keywords;
        this.employmentTypes = employmentTypes;
        this.searchFilters = searchFilters;
    }

    public void update(UserPreferences preferences) {
        this.keywords = preferences.getKeywords();
        this.employmentTypes = preferences.getEmploymentTypes();
        this.searchFilters = preferences.getSearchFilters();
    }

    public UserId getUserId() {
        return userId;
    }

    public Keywords getKeywords() {
        return keywords;
    }

    public EmploymentTypes getEmploymentTypes() {
        return employmentTypes;
    }

    public SearchFilters getSearchFilters() {
        return searchFilters;
    }

    public Country getSearchFiltersCountry() {
        return searchFilters.getCountry();
    }

    public Boolean getSearchFiltersRemoteWork() {
        return searchFilters.getRemoteWork();
    }

    public ExcludeJobPublishers getSearchFiltersExcludeJobPublishers() {
        return searchFilters.getExcludeJobPublishers();
    }
}