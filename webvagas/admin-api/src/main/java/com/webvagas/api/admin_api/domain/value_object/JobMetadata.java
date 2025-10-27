package com.webvagas.api.admin_api.domain.value_object;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Objects;

public class JobMetadata {
    private String title;
    private String description;
    private Posted posted;
    private Boolean isRemote;
    private String applyLink;

    public JobMetadata(String title, String description, Posted posted, Boolean isRemote, String applyLink) {
        Objects.requireNonNull(title, "Title cannot be null.");

        Objects.requireNonNull(description, "Description cannot be null.");

        Objects.requireNonNull(posted, "Posted cannot be null.");

        Objects.requireNonNull(applyLink, "Apply link cannot be null.");

        if (applyLink.isEmpty())
            throw new IllegalArgumentException("Apply link cannot be empty.");

        if (!applyLink.matches("^https?://.*"))
            throw new IllegalArgumentException("Apply link must be a valid URL.");

        this.title = title;
        this.description = description;
        this.posted = posted;
        this.isRemote = isRemote;
        this.applyLink = applyLink;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Posted getPosted() {
        return posted;
    }

    public Boolean isRemote() {
        return isRemote;
    }

    public String getPostedHumanReadable() {
        return posted.getHumanReadable();
    }

    public Timestamp getPostedTimestamp() {
        return posted.getTimestamp();
    }

    public OffsetDateTime getPostedDateTime() {
        return posted.getDateTimeutc();
    }

    public String getApplyLink() {
        return applyLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof JobMetadata))
            return false;

        JobMetadata that = (JobMetadata) o;

        return Objects.equals(that.title, this.title)
                && Objects.equals(that.description, this.description)
                && Objects.equals(that.posted, this.posted)
                && Objects.equals(that.isRemote, this.isRemote)
                && Objects.equals(that.applyLink, this.applyLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, posted, isRemote, applyLink);
    }
}