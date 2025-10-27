package com.webvagas.urlshortener_api.api.url_shortener;

import java.time.LocalDateTime;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.webvagas.urlshortener_api.UrlShortenerApiApplication;
import com.webvagas.urlshortener_api.infrastructure.repository.UrlShortenerRepository;
import com.webvagas.urlshortener_api.persistence.shourt_url.ShortUrlEntity;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        UrlShortenerApiApplication.class })
@Testcontainers
class UrlShortenerIT {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    @BeforeEach
    void setUp() {
        urlShortenerRepository.deleteAll();
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldCreateShortUrlAndReturnDbProjection() {
        String payload = """
                    [
                        {
                            "originalUrl": "https://www.google.com.br"
                        }
                    ]
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/shorten/batch")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void shouldReturnRedirectResponseWhenShortUrlHaveOriginUrl() {
        ShortUrlEntity entity = new ShortUrlEntity();

        entity.setCode("abc123");

        entity.setOriginalUrl("https://www.google.com.br");

        entity.setCreatedAt(LocalDateTime.now());

        urlShortenerRepository.save(entity);

        given()
                .redirects().follow(false)
                .get("/" + entity.getCode())
                .then()
                .statusCode(308)
                .header("Location", "https://www.google.com.br");
    }

    @Test
    void shouldReturnNotFoundWhenShortUrlNotHaveOriginUrl() {
        given()
                .get("/" + "abc123")
                .then()
                .statusCode(404);
    }
}