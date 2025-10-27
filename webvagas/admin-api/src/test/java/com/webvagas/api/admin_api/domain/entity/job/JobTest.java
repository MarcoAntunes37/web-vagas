package com.webvagas.api.admin_api.domain.entity.job;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webvagas.api.admin_api.domain.entity.job.enums.EmploymentType;
import com.webvagas.api.admin_api.domain.value_object.Employer;
import com.webvagas.api.admin_api.domain.value_object.JobEmploymentTypes;
import com.webvagas.api.admin_api.domain.value_object.JobId;
import com.webvagas.api.admin_api.domain.value_object.JobMetadata;
import com.webvagas.api.admin_api.domain.value_object.Location;
import com.webvagas.api.admin_api.domain.value_object.Posted;
import com.webvagas.api.admin_api.domain.value_object.Publisher;
import com.webvagas.api.admin_api.domain.value_object.SalaryRange;

import static org.assertj.core.api.Assertions.assertThat;

public class JobTest {
    private JobId id;
    private Employer employer;
    private Publisher publisher;
    private JobMetadata metadata;
    private JobEmploymentTypes employmentTypes;
    private Location location;
    private SalaryRange salaryRange;

    @BeforeEach
    void Setup() {
        Posted postedObj = new Posted(
                "date",
                new Timestamp(System.currentTimeMillis()),
                OffsetDateTime.now());

        SalaryRange salaryRangeObj = new SalaryRange(
                1000, 2000, 3000, "BRL", "monthly");

        JobMetadata jobMetadataObj = new JobMetadata(
                "title", "description", postedObj, true, "https://applyLink");

        Location locationObj = new Location(
                "region", "city", "state", "country", 1.0, 1.0);

        id = new JobId(UUID.randomUUID().toString());
        employer = new Employer("name", "logo", "website", "linkedin");
        publisher = new Publisher("name");
        metadata = jobMetadataObj;
        employmentTypes = new JobEmploymentTypes(Set.of(EmploymentType.CONTRACTOR, EmploymentType.FULLTIME));
        location = locationObj;
        salaryRange = salaryRangeObj;
    }

    @Test
    void shouldCreateJob() {
        Job job = new Job(id, employer, publisher, metadata, employmentTypes, location, salaryRange);

        assertThat(job).isNotNull();
    }

    @Test
    void shouldDelegateEmployerMethods() {
        Job job = new Job(id, employer, publisher, metadata, employmentTypes, location, salaryRange);

        assertThat(job.getEmployerName()).isEqualTo(employer.getName());
        assertThat(job.getEmployerLogo()).isEqualTo(employer.getLogo());
        assertThat(job.getEmployerWebsite()).isEqualTo(employer.getWebsite());
        assertThat(job.getEmployerLinkedin()).isEqualTo(employer.getLinkedin());
    }

    @Test
    void shouldDelegatePublisherMethods() {
        Job job = new Job(id, employer, publisher, metadata, employmentTypes, location, salaryRange);

        assertThat(job.getPublisherName()).isEqualTo(publisher.getName());
    }

    @Test
    void shouldDelegateMetadataMethods() {
        Job job = new Job(id, employer, publisher, metadata, employmentTypes, location, salaryRange);

        assertThat(job.getMetadataTitle()).isEqualTo(metadata.getTitle());
        assertThat(job.getMetadataDescription()).isEqualTo(metadata.getDescription());
        assertThat(job.getMetadataApplyLink()).isEqualTo(metadata.getApplyLink());
        assertThat(job.getMetadataIsRemote()).isEqualTo(metadata.isRemote());
        assertThat(job.getMetadataPosted()).isEqualTo(metadata.getPosted());
        assertThat(job.getMetadataPostedHumanReadable()).isEqualTo(metadata.getPostedHumanReadable());
        assertThat(job.getMetadataPostedTimestamp()).isEqualTo(metadata.getPostedTimestamp());
        assertThat(job.getMetadataPostedDateTime()).isEqualTo(metadata.getPostedDateTime());
    }

    @Test
    void shouldDelegateLocationMethods() {
        Job job = new Job(id, employer, publisher, metadata, employmentTypes, location, salaryRange);

        assertThat(job.getLocationRegion()).isEqualTo(location.getRegion());
        assertThat(job.getLocationCity()).isEqualTo(location.getCity());
        assertThat(job.getLocationState()).isEqualTo(location.getState());
        assertThat(job.getLocationCountry()).isEqualTo(location.getCountry());
        assertThat(job.getLocationLatitude()).isEqualTo(location.getLatitude());
        assertThat(job.getLocationLongitude()).isEqualTo(location.getLongitude());
    }

    @Test
    void shouldDelegateSalaryRangeMethods() {
        Job job = new Job(id, employer, publisher, metadata, employmentTypes, location, salaryRange);

        assertThat(job.getSalaryRangeMin()).isEqualTo(salaryRange.getMin());
        assertThat(job.getSalaryRangeMax()).isEqualTo(salaryRange.getMax());
        assertThat(job.getSalaryRangeCurrency()).isEqualTo(salaryRange.getCurrency());
        assertThat(job.getSalaryRangePeriod()).isEqualTo(salaryRange.getPeriod());
    }
}