package com.webvagas.api.admin_api.application.abstraction.base_message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import com.webvagas.api.admin_api.domain.entity.job.dto.GetJobResponse;
import com.webvagas.api.admin_api.domain.entity.job.dto.GetJobsRequest;
import com.webvagas.api.admin_api.domain.entity.job.dto.GetJobsResponse;
import com.webvagas.api.admin_api.domain.entity.url_shortener.dto.CreateUrlShortenerRequest;
import com.webvagas.api.admin_api.domain.entity.user.dto.GetUserByRoleResponse;
import com.webvagas.api.admin_api.domain.entity.user.dto.UserAttributes;
import com.webvagas.api.admin_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.webvagas.api.admin_api.infrastructure.integration.jsearch.JSearchClient;
import com.webvagas.api.admin_api.infrastructure.integration.urlshortener.UrlShortenerClient;
import com.webvagas.api.admin_api.infrastructure.integration.webvagas.JobsUserClient;
import com.webvagas.api.admin_api.infrastructure.integration.webvagas.UserPreferencesClient;

@ExtendWith(MockitoExtension.class)
class BaseMessageServiceTest {
        @Mock
        private JSearchClient jsearchClient;

        @Mock
        private JobsUserClient jobsUserClient;

        @Mock
        private UserPreferencesClient userPreferencesClient;

        @Mock
        private UrlShortenerClient urlShortenerClient;

        UserPreferencesGetResponse preferences;

        TestMessageService service;

        class TestMessageService extends BaseMessageService {
                public TestMessageService(
                                JSearchClient jsearchClient,
                                JobsUserClient jobsUserClient,
                                UserPreferencesClient userPreferencesClient,
                                UrlShortenerClient urlShortenerClient,
                                String accountSid,
                                String authToken,
                                String twilioNumber,
                                String jobListStringBase,
                                String messageServiceId,
                                String templateWithJobsId,
                                String templateWithoutJobsId) {
                        super(jsearchClient, jobsUserClient, userPreferencesClient, urlShortenerClient,
                                        accountSid, authToken, twilioNumber, jobListStringBase,
                                        messageServiceId, templateWithJobsId, templateWithoutJobsId);
                }
        }

        @BeforeEach
        void setUp() {
                service = spy(new TestMessageService(
                                jsearchClient,
                                jobsUserClient,
                                userPreferencesClient,
                                urlShortenerClient,
                                "sid", "token", "+123456", "jobListBase",
                                "serviceId", "templateWithJobsId", "templateWithoutJobsId"));

                preferences = new UserPreferencesGetResponse(
                                "test,test", "FULLTIME, PARTTIME", "BR", false, "test,test");
        }

        @Test
        void shouldFetchJobsAndReturnJobList() {
                GetJobResponse job1 = new GetJobResponse(
                                "id", "employerName", "jobTitle", OffsetDateTime.now(), "https://jobapplylink.com.br");
                GetJobResponse job2 = new GetJobResponse(
                                "id", "employerName", "jobTitle", OffsetDateTime.now(), "https://jobapplylink.com.br");

                List<GetJobResponse> jobs = List.of(job1, job2);

                GetJobsResponse responseBody = mock(GetJobsResponse.class);

                when(responseBody.data()).thenReturn(jobs);

                ResponseEntity<GetJobsResponse> responseEntity = ResponseEntity.ok(responseBody);

                when(jsearchClient.getJobs(any(GetJobsRequest.class)))
                                .thenReturn(responseEntity);

                List<GetJobResponse> result = service.fetchJobs(preferences, 1);

                assertThat(result.size()).isEqualTo(2);
                assertThat(result.contains(job1)).isTrue();
                assertThat(result.contains(job2)).isTrue();
        }

        @Test
        void shouldFetchJobsReturnEmptyListWhenNoJobsFound() {
                GetJobsResponse responseBody = mock(GetJobsResponse.class);

                when(responseBody.data()).thenReturn(List.of());

                ResponseEntity<GetJobsResponse> responseEntity = ResponseEntity.ok(responseBody);

                when(jsearchClient.getJobs(any(GetJobsRequest.class)))
                                .thenReturn(responseEntity);

                List<GetJobResponse> result = service.fetchJobs(preferences, 1);

                assertThat(result.size()).isEqualTo(0);
        }

        @Test
        void shouldFilterNewJobsForUserWhenJobsListIsNotEmpty() throws JsonProcessingException {
                String userId = "userId";

                GetJobResponse job1 = new GetJobResponse(
                                "id", "employerName", "jobTitle", OffsetDateTime.now(), "https://jobapplylink.com.br");
                GetJobResponse job2 = new GetJobResponse(
                                "id", "employerName", "jobTitle", OffsetDateTime.now(), "https://jobapplylink.com.br");

                List<GetJobResponse> jobs = List.of(job1, job2);

                doReturn(jobs).when(service).filterNewJobsForUser(anyString(), anyList(), anyInt(), anyString());

                List<GetJobResponse> result = service.filterNewJobsForUser(userId, jobs, 2, "token");

                assertThat(result.size()).isEqualTo(2);
                assertThat(result.contains(job1)).isTrue();
                assertThat(result.contains(job2)).isTrue();
        }

        @Test
        void shouldFilterNewJobsForUserReturnEmptyListWhenReceivedStatusIsNull() {
                GetJobsResponse responseBody = mock(GetJobsResponse.class);

                when(responseBody.data()).thenReturn(List.of());

                ResponseEntity<GetJobsResponse> responseEntity = ResponseEntity.ok(responseBody);

                when(jsearchClient.getJobs(any(GetJobsRequest.class)))
                                .thenReturn(responseEntity);

                List<GetJobResponse> result = service.fetchJobs(preferences, 1);

                assertThat(result.size()).isEqualTo(0);
        }

        @Test
        void shouldFilterNewJobsForUserReturnEmptyListWhenShortUrlListIsNullOrEmpty() {
                GetJobsResponse responseBody = mock(GetJobsResponse.class);

                when(responseBody.data()).thenReturn(List.of());

                ResponseEntity<GetJobsResponse> responseEntity = ResponseEntity.ok(responseBody);

                when(jsearchClient.getJobs(any(GetJobsRequest.class)))
                                .thenReturn(responseEntity);

                List<GetJobResponse> result = service.fetchJobs(preferences, 1);

                assertThat(result.size()).isEqualTo(0);
        }

        @Test
        void shouldExtractJobsIds() {
                GetJobResponse job1 = new GetJobResponse(
                                "id1", "employerName", "jobTitle", OffsetDateTime.now(), "https://jobapplylink.com.br");
                GetJobResponse job2 = new GetJobResponse(
                                "id2", "employerName", "jobTitle", OffsetDateTime.now(), "https://jobapplylink.com.br");

                List<GetJobResponse> jobs = List.of(job1, job2);

                List<String> result = service.extractJobsIds(jobs);

                assertThat(result.size()).isEqualTo(2);
                assertThat(result.contains(job1.id())).isTrue();
                assertThat(result.contains(job2.id())).isTrue();
        }

        @Test
        void shouldExtractJobsUrl() {
                GetJobResponse job1 = new GetJobResponse(
                                "id1", "employerName", "jobTitle", OffsetDateTime.now(), "https://jobapplylink.com.br");
                GetJobResponse job2 = new GetJobResponse(
                                "id2", "employerName", "jobTitle", OffsetDateTime.now(), "https://jobapplylink.com.br");

                List<GetJobResponse> jobs = List.of(job1, job2);

                List<CreateUrlShortenerRequest> result = service.extractJobsUrl(jobs);

                assertThat(result.size()).isEqualTo(2);
                assertThat(result.contains(new CreateUrlShortenerRequest(job1.jobApplyLink()))).isTrue();
                assertThat(result.contains(new CreateUrlShortenerRequest(job2.jobApplyLink()))).isTrue();
        }

        @Test
        void shouldCreateMessageWithJobs() throws Exception {
                try (MockedStatic<Message> mocked = mockStatic(Message.class)) {
                        MessageCreator creatorMock = mock(MessageCreator.class);

                        Message messageMock = mock(Message.class);

                        mocked.when(() -> Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), anyString()))
                                        .thenReturn(creatorMock);

                        when(creatorMock.setMessagingServiceSid(anyString())).thenReturn(creatorMock);
                        when(creatorMock.setContentSid(anyString())).thenReturn(creatorMock);
                        when(creatorMock.setContentVariables(anyString())).thenReturn(creatorMock);

                        when(creatorMock.create()).thenReturn(messageMock);

                        service.createMessageWithJobs("+5511999999999", List.of("Job1"), "Marco");

                        verify(creatorMock).create();
                }
        }

        @Test
        void shouldCreateMessageWithoutJobs() throws Exception {
                try (MockedStatic<Message> mocked = mockStatic(Message.class)) {
                        MessageCreator creatorMock = mock(MessageCreator.class);

                        Message messageMock = mock(Message.class);

                        mocked.when(() -> Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), anyString()))
                                        .thenReturn(creatorMock);

                        when(creatorMock.setMessagingServiceSid(anyString())).thenReturn(creatorMock);
                        when(creatorMock.setContentSid(anyString())).thenReturn(creatorMock);
                        when(creatorMock.setContentVariables(anyString())).thenReturn(creatorMock);

                        when(creatorMock.create()).thenReturn(messageMock);

                        service.createMessageWithoutJobs("+5511999999999", "Marco");

                        verify(creatorMock).create();
                }
        }

        @Test
        void shouldProcessUserDataWhenUserHasJobsAndPreferences() throws Exception {
                UserPreferencesGetResponse preferencesMock = mock(UserPreferencesGetResponse.class);
                when(preferencesMock.keywords()).thenReturn("keyword1,keyword2,keyword3");

                GetUserByRoleResponse userMock = mock(GetUserByRoleResponse.class);
                UserAttributes attributesMock = mock(UserAttributes.class);
                when(userMock.id()).thenReturn("user123");
                when(userMock.attributes())
                                .thenReturn(new UserAttributes(new String[] { "+5511999999999" }, new String[] {}));
                when(attributesMock.phone()).thenReturn(new String[] { "+5511999999999" });
                when(userMock.attributes()).thenReturn(attributesMock);
                when(userMock.firstName()).thenReturn("Test");

                when(userPreferencesClient.findUserPreferences(eq("user123"), anyString()))
                                .thenReturn(Optional.of(preferencesMock));

                GetJobResponse job1 = new GetJobResponse("id1", "employerName", "jobTitle", OffsetDateTime.now(),
                                "https://jobapplylink.com.br");

                List<GetJobResponse> newJobs = List.of(job1);

                doReturn(newJobs).when(service).fetchJobs(any(), anyInt());
                doReturn(newJobs).when(service).filterNewJobsForUser(anyString(), anyList(), anyInt(), anyString());

                try (MockedStatic<Message> messageStatic = mockStatic(Message.class)) {
                        MessageCreator creatorMock = mock(MessageCreator.class);
                        Message messageMock = mock(Message.class);

                        messageStatic.when(() -> Message.creator(any(PhoneNumber.class), any(PhoneNumber.class),
                                        anyString()))
                                        .thenReturn(creatorMock);

                        when(creatorMock.setMessagingServiceSid(anyString())).thenReturn(creatorMock);
                        when(creatorMock.setContentSid(anyString())).thenReturn(creatorMock);
                        when(creatorMock.setContentVariables(anyString())).thenReturn(creatorMock);
                        when(creatorMock.create()).thenReturn(messageMock);

                        service.processUserData(userMock, 1, "token123");

                        verify(creatorMock).create();
                        verify(service).fetchJobs(any(), anyInt());
                        verify(service).filterNewJobsForUser(anyString(), anyList(), anyInt(), anyString());
                        verify(jobsUserClient).createJobUser(eq("user123"), eq(List.of("id1")), eq("token123"));
                }
        }
}