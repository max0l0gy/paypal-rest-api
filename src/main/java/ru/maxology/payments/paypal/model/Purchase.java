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
public class Purchase extends DomainJson {
    @JsonProperty("reference_id")
    private String referenceId;
    private Amount amount;
    private String description;
    private Payments payments;

    @SneakyThrows
    public static Purchase fromJsonString(String json) {
        return MAPPER.readValue(json, Purchase.class);
    }
}
