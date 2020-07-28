package ru.maxology.payments.paypal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.vertx.core.http.HttpMethod;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link extends DomainJson {
    private String href;
    private String rel;
    private HttpMethod method;

    @SneakyThrows
    public static Link fromJsonString(String json) {
        return MAPPER.readValue(json, Link.class);
    }
}
