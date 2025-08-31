package com.flashvagas.api.admin_api.infrastructure.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("null")
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    @NonNull
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        System.out.println("===========================request begin================================================");
        System.out.println("URI         : " + request.getURI());
        System.out.println("Method      : " + request.getMethod());
        System.out.println("Headers     : " + request.getHeaders());
        System.out.println("Request body: " + new String(body, StandardCharsets.UTF_8));
        System.out.println("==========================request end================================================");
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        System.out.println("===========================response begin==============================================");
        System.out.println("Status code  : " + response.getStatusCode());
        System.out.println("Status text  : " + response.getStatusText());
        System.out.println("Headers      : " + response.getHeaders());
        String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        System.out.println("Response body: " + body);
        System.out.println("===========================response end================================================");
    }
}
