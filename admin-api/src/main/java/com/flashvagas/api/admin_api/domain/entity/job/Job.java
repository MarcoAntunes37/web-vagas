package com.flashvagas.api.admin_api.domain.entity.job;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import com.flashvagas.api.admin_api.domain.value_object.Employer;
import com.flashvagas.api.admin_api.domain.value_object.EmploymentTypes;
import com.flashvagas.api.admin_api.domain.value_object.JobId;
import com.flashvagas.api.admin_api.domain.value_object.JobMetadata;
import com.flashvagas.api.admin_api.domain.value_object.Location;
import com.flashvagas.api.admin_api.domain.value_object.Posted;
import com.flashvagas.api.admin_api.domain.value_object.Publisher;
import com.flashvagas.api.admin_api.domain.value_object.SalaryRange;

public class Job {
    private JobId id;
    private Employer employer;
    private Publisher publisher;
    private JobMetadata metadata;
    private EmploymentTypes employmentTypes;
    private Location location;
    private SalaryRange salaryRange;

    public Job(JobId id, Employer employer, Publisher publisher, JobMetadata metadata,
            EmploymentTypes employmentTypes, Location location, SalaryRange salaryRange) {
        this.id = id;
        this.employer = employer;
        this.publisher = publisher;
        this.metadata = metadata;
        this.employmentTypes = employmentTypes;
        this.location = location;
        this.salaryRange = salaryRange;
    }

    public JobId getId() {
        return id;
    }

    public Employer getEmployer() {
        return employer;
    }

    public String getEmployerName() {
        return employer.getName();
    }

    public String getEmployerLogo() {
        return employer.getLogo();
    }

    public String getEmployerWebsite() {
        return employer.getWebsite();
    }

    public String getEmployerLinkedin() {
        return employer.getLinkedin();
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public String getPublisherName() {
        return publisher.getName();
    }

    public JobMetadata getMetadata() {
        return metadata;
    }

    public String getMetadataTitle() {
        return metadata.getTitle();
    }

    public String getMetadataDescription() {
        return metadata.getDescription();
    }

    public Posted getMetadataPosted() {
        return metadata.getPosted();
    }

    public String getMetadataPostedHumanReadable() {
        return metadata.getPostedHumanReadable();
    }

    public Timestamp getMetadataPostedTimestamp() {
        return metadata.getPostedTimestamp();
    }

    public OffsetDateTime getMetadataPostedDateTime() {
        return metadata.getPostedDateTime();
    }

    public Boolean getMetadataIsRemote() {
        return metadata.isRemote();
    }

    public String getMetadataApplyLink() {
        return metadata.getApplyLink();
    }

    public EmploymentTypes getEmploymentTypes() {
        return employmentTypes;
    }

    public Location getLocation() {
        return location;
    }

    public String getLocationRegion() {
        return location.getRegion();
    }

    public String getLocationCity() {
        return location.getCity();
    }

    public String getLocationCountry() {
        return location.getCountry();
    }

    public String getLocationState() {
        return location.getState();
    }

    public double getLocationLatitude() {
        return location.getLatitude();
    }

    public double getLocationLongitude() {
        return location.getLongitude();
    }

    public SalaryRange getSalaryRange() {
        return salaryRange;
    }
}