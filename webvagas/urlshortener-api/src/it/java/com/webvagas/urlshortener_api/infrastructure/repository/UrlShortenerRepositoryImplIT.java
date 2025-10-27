package com.webvagas.urlshortener_api.infrastructure.repository;

import com.webvagas.urlshortener_api.infrastructure.repository.projections.UrlShortenerBatchInsertProjection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UrlShortenerRepositoryImplIT {
    @Container
    @SuppressWarnings("resource")
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private JdbcTemplate jdbcTemplate;

    private UrlShortenerRepositoryImpl repository;

    @BeforeAll
    void Setup() {
        DataSource dataSource = DataSourceBuilder.create()
                .url(postgres.getJdbcUrl())
                .username(postgres.getUsername())
                .password(postgres.getPassword())
                .driverClassName(postgres.getDriverClassName())
                .build();

        jdbcTemplate = new JdbcTemplate(dataSource);

        repository = new UrlShortenerRepositoryImpl(jdbcTemplate);

        jdbcTemplate.execute("""
                    CREATE TABLE short_url (
                        id SERIAL PRIMARY KEY,
                        code TEXT NOT NULL,
                        original_url TEXT NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT now()
                    );
                """);
    }

    @Test
    void shouldInsertBatchAndReturnInsertedRows() {
        List<String> codes = List.of("abc123", "xyz456");

        List<String> urls = List.of("https://site1.com", "https://site2.com");

        List<UrlShortenerBatchInsertProjection> results = repository.insertBatchAndReturn(codes, urls);

        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(UrlShortenerBatchInsertProjection::getCode)
                .containsExactlyInAnyOrder("abc123", "xyz456");
        assertThat(results)
                .extracting(UrlShortenerBatchInsertProjection::getOriginalUrl)
                .containsExactlyInAnyOrder("https://site1.com", "https://site2.com");

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM short_url", Integer.class);

        assertThat(count).isEqualTo(2);
    }

    @Test
    void shouldThrowExceptionWhenListsAreDifferentSizes() {
        List<String> codes = List.of("abc123");

        List<String> urls = List.of("https://site1.com", "https://site2.com");

        assertThatThrownBy(() -> repository.insertBatchAndReturn(codes, urls))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Lists cannot have different sizes.");
    }

    @Test
    void shouldThrowExceptionWhenListsAreEmpty() {
        assertThatThrownBy(() -> repository.insertBatchAndReturn(List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Codes and Urls lists cannot be empty.");
    }
}