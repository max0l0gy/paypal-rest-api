package ru.maxology.payments.paypal;

import io.quarkus.arc.config.ConfigProperties;
import ru.maxology.payments.paypal.model.Token;

import java.util.Base64;

@ConfigProperties(prefix = "paypal-api")
public class PayPalProperties {
    public static final String GET_TOKEN_DATA = "grant_type=client_credentials";
    public String clientId;
    public String secret;
    public String sandbox;
    public String live;

    public String basicAuthorization() {
        return "Basic " +
                Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes());
    }
    public String getAuthorization(Token token) {
        return "Bearer " + token.access_token;
    }
}
