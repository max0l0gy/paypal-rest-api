package ru.maxology.payments.paypal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token extends DomainJson {
    public int expires_in;
    public String access_token;
    @SneakyThrows
    public static Token fromJsonString(String json){
        return MAPPER.readValue(json, Token.class);
    }
}
