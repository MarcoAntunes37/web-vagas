package com.webvagas.api.admin_api.domain.value_object;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Objects;

public class Posted {
    private String humanReadable;
    private Timestamp timestamp;
    private OffsetDateTime dateTimeutc;

    public Posted(String humanReadable, Timestamp timestamp, OffsetDateTime dateTimeutc) {
        Objects.requireNonNull(humanReadable, "Human readable cannot be null.");

        Objects.requireNonNull(timestamp, "Timestamp cannot be null.");

        Objects.requireNonNull(dateTimeutc, "Date time utc cannot be null.");

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Posted))
            return false;

        Posted that = (Posted) o;

        return Objects.equals(that.humanReadable, this.humanReadable)
                && Objects.equals(that.timestamp, this.timestamp)
                && Objects.equals(that.dateTimeutc, this.dateTimeutc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanReadable, timestamp, dateTimeutc);
    }
}