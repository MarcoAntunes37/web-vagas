package com.webvagas.api.admin_api.application.service.plan_message;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.webvagas.api.admin_api.domain.entity.user.dto.GetUserByRoleResponse;
import com.webvagas.api.admin_api.domain.entity.user.dto.UserAttributes;
import com.webvagas.api.admin_api.infrastructure.integration.jsearch.JSearchClient;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_auth.KeycloakAuthClientImpl;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_user.KeycloakUserClientImpl;
import com.webvagas.api.admin_api.infrastructure.integration.urlshortener.UrlShortenerClient;
import com.webvagas.api.admin_api.infrastructure.integration.webvagas.JobsUserClient;
import com.webvagas.api.admin_api.infrastructure.integration.webvagas.UserPreferencesClient;

import java.util.List;

public class StartMessageServiceImplTest {
    @Mock
    private KeycloakAuthClientImpl kcAuthClient;

    @Mock
    private KeycloakUserClientImpl kcUserClient;

    @Mock
    private JobsUserClient jobsUserClient;

    @Mock
    private JSearchClient jsearchClient;

    @Mock
    private UserPreferencesClient userPreferencesClient;

    @Mock
    private UrlShortenerClient urlShortenerClient;

    private StartMessageServiceImpl startMessageService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        startMessageService = new StartMessageServiceImpl(
                kcAuthClient,
                kcUserClient,
                jobsUserClient,
                jsearchClient,
                userPreferencesClient,
                urlShortenerClient,
                "dummySid",
                "dummyToken",
                "+5511999999999",
                "jobListBase",
                "dummyMessageServiceId",
                "templateWithJobsId",
                "templateWithoutJobsId");
        ReflectionTestUtils.setField(startMessageService, "startJobsQuantityPerMessage", 3);
    }

    @Test
    void shouldSendMessagesWhenThereAreUsers() throws Exception {
        when(kcAuthClient.getAccessToken()).thenReturn("fake-token");

        GetUserByRoleResponse user1 = mock(GetUserByRoleResponse.class);
        when(user1.id()).thenReturn("user1");

        when(kcUserClient.getUsersByRole("plan-start", "fake-token"))
                .thenReturn(List.of(user1));

        UserAttributes attributes = mock(UserAttributes.class);

        when(user1.id()).thenReturn("user1");
        when(user1.attributes()).thenReturn(attributes);
        when(attributes.phone()).thenReturn(new String[] { "+5511999999999" });

        startMessageService.sendMessages();

        verify(kcAuthClient, times(1)).getAccessToken();
        verify(kcUserClient, times(1)).getUsersByRole("plan-start", "fake-token");
    }

    @Test
    void shouldNotSendMessagesWhenThereAreNoUsers() throws Exception {
        when(kcAuthClient.getAccessToken()).thenReturn("fake-token");
        when(kcUserClient.getUsersByRole("plan-start", "fake-token"))
                .thenReturn(List.of());

        startMessageService.sendMessages();

        verify(kcAuthClient, times(1)).getAccessToken();
        verify(kcUserClient, times(1)).getUsersByRole("plan-start", "fake-token");
    }
}