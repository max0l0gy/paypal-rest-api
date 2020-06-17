package ru.maxology.payments.paypal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class Token {
    private static ObjectMapper mapper = new ObjectMapper();
    public int expires_in;
    public String access_token;
    @SneakyThrows
    public static Token fromJsonString(String json){
        return mapper.readValue(json, Token.class);
    }
}
