package ru.maxology.payments.paypal;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.maxology.payments.paypal.model.Token;
import ru.maxology.payments.paypal.rest.client.PayPalService;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class PayPalResourceTest {

    @InjectMock
    @RestClient
    PayPalService payPalService;
    @Inject
    PayPalProperties payPalProperties;

    public void setupMock() {
        String jsonResp = TestUtil.getBody("response/payPalToken.json");
        Token token = Token.fromJsonString(jsonResp);

        Mockito.when(payPalService
                .token(payPalProperties.basicAuthorization(),
                        PayPalProperties.GET_TOKEN_DATA)).thenReturn(token);
    }

    @Test
    public void testOverrideToken() {
        setupMock();
        String jsonResp = TestUtil.getBody("response/payPalToken.json");
        Token token = Token.fromJsonString(jsonResp);

        Token restTokenResponse = payPalService
                .token(payPalProperties.basicAuthorization(),
                        PayPalProperties.GET_TOKEN_DATA);
        assertEquals(token.access_token, restTokenResponse.access_token);
    }

    @Test
    public void testTokenEndpoint() {
        setupMock();
        given()
                .when().get("/v1/token")
                .then()
                .statusCode(200)
                .body("access_token", is("A21AAHyNxN2jFKrikdPrk"));
    }

}