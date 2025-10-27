package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class JobMetadataTest {
    private String title;
    private String description;
    private Posted posted;
    private Boolean isRemote;
    private String applyLink;

    @BeforeEach
    void setUp() {
        title = "title";
        description = "description";
        posted = new Posted(
                "humanReadable",
                new Timestamp(System.currentTimeMillis()),
                OffsetDateTime.now());
        isRemote = true;
        applyLink = "https://applyLink";
    }

    @Test
    void shouldFailToCreateWhenWhenTitleIsNull() {
        title = null;

        assertThatThrownBy(() -> new JobMetadata(title, description, posted, isRemote, applyLink))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Title cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenDescriptionIsNull() {
        description = null;

        assertThatThrownBy(() -> new JobMetadata(title, description, posted, isRemote, applyLink))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Description cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenPostedIsNull() {
        posted = null;

        assertThatThrownBy(() -> new JobMetadata(title, description, posted, isRemote, applyLink))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Posted cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenApplyLinkIsNull() {
        applyLink = null;

        assertThatThrownBy(() -> new JobMetadata(title, description, posted, isRemote, applyLink))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Apply link cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenApplyLinkIsEmpty() {
        applyLink = "";

        assertThatThrownBy(() -> new JobMetadata(title, description, posted, isRemote, applyLink))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Apply link cannot be empty.");
    }

    @Test
    void shouldFailToCreateWhenWhenApplyLinkIsNotAValidUrl() {
        applyLink = "applyLink";

        assertThatThrownBy(() -> new JobMetadata(title, description, posted, isRemote, applyLink))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Apply link must be a valid URL.");
    }

    @Test
    void shouldCreateJobMetadata() {
        JobMetadata jobMetadata = new JobMetadata(title, description, posted, isRemote, applyLink);

        assertThat(jobMetadata).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        JobMetadata jobMetadata = new JobMetadata(title, description, posted, isRemote, applyLink);

        title = "title2";
        description = "description2";
        posted = new Posted(
                "humanReadable2",
                new Timestamp(System.currentTimeMillis()),
                OffsetDateTime.now());
        isRemote = false;
        applyLink = "https://applyLink2";

        JobMetadata jobMetadata2 = new JobMetadata(title, description, posted, isRemote, applyLink);

        assertThat(jobMetadata.equals(jobMetadata2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        JobMetadata jobMetadata = new JobMetadata(title, description, posted, isRemote, applyLink);

        JobMetadata jobMetadata2 = new JobMetadata(title, description, posted, isRemote, applyLink);

        assertThat(jobMetadata.equals(jobMetadata2)).isTrue();
    }
}