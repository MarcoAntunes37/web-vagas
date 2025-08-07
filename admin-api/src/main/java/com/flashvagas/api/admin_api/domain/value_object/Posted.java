package com.flashvagas.api.admin_api.domain.value_object;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

public class Posted {
    private String humanReadable;
    private Timestamp timestamp;
    private OffsetDateTime dateTimeutc;

    public Posted(String humanReadable, Timestamp timestamp, OffsetDateTime dateTimeutc) {
        this.humanReadable = humanReadable;
        this.timestamp = timestamp;
        this.dateTimeutc = dateTimeutc;
    }

    public String getHumanReadable() {
        return humanReadable;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public OffsetDateTime getDateTimeutc() {
        return dateTimeutc;
    }
}
