package ru.maxology.payments.paypal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order extends DomainJson {
    private String id;
    private OrderIntent intent;
    private OrderStatus status;
    @JsonProperty("purchase_units")
    private List<Purchase> purchaseUnits;

    @SneakyThrows
    public static Order fromJsonString(String json) {
        return MAPPER.readValue(json, Order.class);
    }
}
