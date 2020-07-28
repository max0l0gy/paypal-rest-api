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
public class SellerReceivableBreakdown extends DomainJson {
    @JsonProperty("gross_amount")
    private Amount grossAmount;
    @JsonProperty("paypal_fee")
    private Amount paypalFee;
    @JsonProperty("net_amount")
    private Amount netAmount;
    @SneakyThrows
    public static SellerReceivableBreakdown fromJsonString(String json) {
        return MAPPER.readValue(json, SellerReceivableBreakdown.class);
    }
}
