package ru.maxology.payments.paypal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Amount extends DomainJson {
    @JsonProperty("currency_code")
    private String currencyCode;
    private String value;
    @SneakyThrows
    public static Amount fromJsonString(String json) {
        return MAPPER.readValue(json, Amount.class);
    }
}
