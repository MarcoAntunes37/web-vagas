package com.webvagas.api.webvagas_api.persistence.user_preferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.webvagas.api.webvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.webvagas_api.domain.value_object.Keywords;
import com.webvagas.api.webvagas_api.persistence.user_preferences.converters.ExcludeJobPublishersConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.UniqueConstraint;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_preferences", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id" })
})
public class UserPreferencesEntity {
    @Transient
    private Keywords keywords;

    @Transient
    private EmploymentTypes employmentTypes;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "keywords", nullable = false)
    private String keywordsDb;

    @Column(name = "employment_types")
    private String employmentTypesDb;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "remote_work", nullable = false)
    private Boolean remoteWork;

    @Convert(converter = ExcludeJobPublishersConverter.class)
    @Column(name = "exclude_job_publishers")
    private ExcludeJobPublishers excludeJobPublishers;

    public void setKeywords(Keywords keywords) {
        this.keywords = keywords;
        this.keywordsDb = keywords.getValue();
    }

    public String getKeywordsDb() {
        return keywordsDb;
    }

    public void setKeywordsDb(String value) {
        this.keywords = new Keywords(value);
        this.keywordsDb = value;
    }

    public EmploymentTypes getEmploymentTypes() {
        return employmentTypes;
    }

    public void setEmploymentTypes(EmploymentTypes values) {
        this.employmentTypes = values;
        this.employmentTypesDb = values.toString();
    }

    public String getEmploymentTypesDb() {
        return employmentTypesDb;
    }

    public void setEmploymentTypesDb(String dbValue) {
        if (dbValue == null || dbValue.isBlank()) {
            this.employmentTypes = new EmploymentTypes(Collections.emptySet());
        } else {
            Set<EmploymentType> values = Arrays.stream(dbValue.split(","))
                    .map(String::trim)
                    .map(EmploymentType::valueOf)
                    .collect(Collectors.toSet());

            this.employmentTypes = new EmploymentTypes(values);
        }
        this.employmentTypesDb = dbValue;
    }
}