package com.flashvagas.api.flashvagas_api.persistence.jobs_user.projections;

public interface JobExistenceProjection {
    String getJobId();

    Boolean getExists();
}