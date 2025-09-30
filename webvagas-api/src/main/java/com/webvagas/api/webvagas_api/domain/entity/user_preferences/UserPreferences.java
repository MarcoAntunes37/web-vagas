package com.webvagas.api.webvagas_api.domain.entity.user_preferences;

import com.webvagas.api.webvagas_api.domain.value_object.Country;
import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.webvagas_api.domain.value_object.Keywords;
import com.webvagas.api.webvagas_api.domain.value_object.SearchFilters;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.domain.value_object.UserPreferencesId;

public class UserPreferences {
    private UserPreferencesId id;
    private UserId userId;
    private Keywords keywords;
    private EmploymentTypes employmentTypes;
    private SearchFilters searchFilters;

    public UserPreferences(
            UserPreferencesId id,
            UserId userId,
            Keywords keywords,
            EmploymentTypes employmentTypes,
            SearchFilters searchFilters) {
        this.id = id;
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

    public UserPreferencesId getId() {
        return id;
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