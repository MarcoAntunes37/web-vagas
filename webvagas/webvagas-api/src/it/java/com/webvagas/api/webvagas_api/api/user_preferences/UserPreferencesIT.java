package com.webvagas.api.webvagas_api.api.user_preferences;

import java.util.UUID;
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

import com.webvagas.api.webvagas_api.WebvagasApiApplication;
import com.webvagas.api.webvagas_api.infrastructure.repository.user_preferences.UserPreferencesRepository;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        WebvagasApiApplication.class }, properties = {
                "spring.rabbitmq.host=disabled",
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration"
        })
@Testcontainers
class UserPreferencesIT {
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
    private UserPreferencesRepository userPreferencesRepository;

    @BeforeEach
    void setUp() {
        userPreferencesRepository.deleteAll();
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldCreateAndRetrieveUserPreferences() {
        String payload = """
                    {
                        "userId": "%s",
                        "keywords": "java,spring,ddd",
                        "employmentTypes": "FULLTIME,CONTRACTOR",
                        "remoteWork": true,
                        "country": "BR",
                        "excludeJobPublishers": "Indeed,LinkedIn"
                    }
                """.formatted(UUID.randomUUID());

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/api/v1/preferences/user/" + extractUserIdFromPayload(payload))
                .then()
                .statusCode(200);
    }

    @Test
    void shouldFailToCreateUserPreferencesIfAlreadyExists() {
        String payload = """
                    {
                        "userId": "%s",
                        "keywords": "java,spring,ddd",
                        "employmentTypes": "FULLTIME,CONTRACTOR",
                        "remoteWork": true,
                        "country": "BR",
                        "excludeJobPublishers": "Indeed,LinkedIn"
                    }
                """.formatted(UUID.randomUUID());

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/api/v1/preferences/user/" + extractUserIdFromPayload(payload))
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/api/v1/preferences/user/" + extractUserIdFromPayload(payload))
                .then()
                .statusCode(500);
    }

    @Test
    void shouldGetUserPreferences() {
        String payload = """
                    {
                        "userId": "%s",
                        "keywords": "java,spring,ddd",
                        "employmentTypes": "FULLTIME,CONTRACTOR",
                        "remoteWork": true,
                        "country": "BR",
                        "excludeJobPublishers": "Indeed,LinkedIn"
                    }
                """.formatted(UUID.randomUUID());

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/api/v1/preferences/user/" + extractUserIdFromPayload(payload))
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .get("/api/v1/preferences/user/" + extractUserIdFromPayload(payload))
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturnNotFoundWhenGetUserPreferencesNotExists() {
        given()
                .contentType(ContentType.JSON)
                .get("/api/v1/preferences/user/" + UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    void shouldUpdateUserPreferences() {
        String payload = """
                    {
                        "userId": "%s",
                        "keywords": "java,spring,ddd",
                        "employmentTypes": "FULLTIME,CONTRACTOR",
                        "remoteWork": true,
                        "country": "BR",
                        "excludeJobPublishers": "Indeed,LinkedIn"
                    }
                """.formatted(UUID.randomUUID());

        String payload2 = """
                    {
                        "keywords": "java,",
                        "employmentTypes": "",
                        "remoteWork": false,
                        "country": "US",
                        "excludeJobPublishers": ""
                    }
                """.formatted(UUID.fromString(extractUserIdFromPayload(payload)));

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/api/v1/preferences/user/" + extractUserIdFromPayload(payload))
                .then()
                .statusCode(200);

        given().contentType(ContentType.JSON)
                .body(payload2)
                .put("/api/v1/preferences/user/" + extractUserIdFromPayload(payload))
                .then()
                .statusCode(204);
    }

    @Test
    void shouldFailToUpdateWhenUserPreferencesNotExists() {
        String payload = """
                    {
                        "userId": "%s",
                        "keywords": "java,spring,ddd",
                        "employmentTypes": "FULLTIME,CONTRACTOR",
                        "remoteWork": true,
                        "country": "BR",
                        "excludeJobPublishers": "Indeed,LinkedIn"
                    }
                """.formatted(UUID.randomUUID());

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .put("/api/v1/preferences/user/" + UUID.randomUUID())
                .then()
                .statusCode(400);
    }

    private String extractUserIdFromPayload(String payload) {
        return payload.substring(
                payload.indexOf("\"userId\": \"") + 11,
                payload.indexOf("\",", payload.indexOf("\"userId\": \"")));
    }
}