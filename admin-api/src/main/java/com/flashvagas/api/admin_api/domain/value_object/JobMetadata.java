package com.flashvagas.api.admin_api.domain.value_object;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

public class JobMetadata {
    private String title;
    private String description;
    private Posted posted;
    private Boolean isRemote;
    private String applyLink;

    public JobMetadata(String title, String description, Posted posted, Boolean isRemote, String applyLink) {
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
}
