package ru.maxology.payments.paypal.rest.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.Response;

@Slf4j
public class UnauthorizedExceptionMapper implements ResponseExceptionMapper<RuntimeException> {
    @Override
    public RuntimeException toThrowable(Response response) {
        if (response.getStatus() == 401) {
            log.error("Handle the HTTP 401 Unauthorized status code: {}", response.readEntity(String.class));
            return new UnauthorizedException("Handle the HTTP 401 Unauthorized status code. The API endpoint issues this status code when it detects an expired token.");
        }
        return null;
    }
}
