package ru.maxology.payments.paypal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payments extends DomainJson {
    List<Capture> captures;
    @SneakyThrows
    public static Payments fromJsonString(String json) {
        return MAPPER.readValue(json, Payments.class);
    }
}
