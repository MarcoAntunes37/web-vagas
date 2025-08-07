package com.flashvagas.api.flashvagas_api.persistence.user_preferences;

import java.util.UUID;

import com.flashvagas.api.flashvagas_api.domain.value_object.EmploymentTypes;
import com.flashvagas.api.flashvagas_api.domain.value_object.ExcludeJobPublishers;
import com.flashvagas.api.flashvagas_api.domain.value_object.Keywords;
import com.flashvagas.api.flashvagas_api.persistence.user_preferences.converters.EmploymentTypesConverter;
import com.flashvagas.api.flashvagas_api.persistence.user_preferences.converters.ExcludeJobPublishersConverter;
import com.flashvagas.api.flashvagas_api.persistence.user_preferences.converters.KeywordsConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.UniqueConstraint;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_preferences_jsearch", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id" })
})
public class UserPreferencesEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Convert(converter = KeywordsConverter.class)
    private Keywords keywords;

    @Convert(converter = EmploymentTypesConverter.class)
    private EmploymentTypes employmentTypes;

    private String country;

    private Boolean remoteWork;

    @Convert(converter = ExcludeJobPublishersConverter.class)
    private ExcludeJobPublishers excludeJobPublishers;
}