package ru.maxology.payments.paypal;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.maxology.payments.paypal.model.Order;
import ru.maxology.payments.paypal.model.RefundResponse;
import ru.maxology.payments.paypal.model.Token;
import ru.maxology.payments.paypal.rest.client.PayPalService;
import ru.maxology.payments.paypal.rest.client.UnauthorizedException;

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
        String jsonResp = TestUtil.getBody("response/paypal-token.json");
        Token token = Token.fromJsonString(jsonResp);

        Mockito.when(payPalService
                .getToken(payPalProperties.basicAuthorization(),
                        PayPalProperties.GET_TOKEN_DATA)).thenReturn(token);

    }

    @Test
    public void testOverrideToken() {
        setupMock();
        String jsonResp = TestUtil.getBody("response/paypal-token.json");
        Token token = Token.fromJsonString(jsonResp);

        Token restTokenResponse = payPalService
                .getToken(payPalProperties.basicAuthorization(),
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

    @Test
    public void testOrderDetails() {
        //when
        //token
        String jsonResp = TestUtil.getBody("response/paypal-token.json");
        Token token = Token.fromJsonString(jsonResp);
        Mockito.when(payPalService
                .getToken(payPalProperties.basicAuthorization(),
                        PayPalProperties.GET_TOKEN_DATA))
                .thenReturn(token);
        //order
        jsonResp = TestUtil.getBody("response/order-details.json");
        Order order = Order.fromJsonString(jsonResp);
        Mockito.when(payPalService
                .getOrder(payPalProperties.getAuthorization(token), "2CV55440UT6646827"))
                .thenReturn(order);
        //then
        given()
                .when().get("/v1/order/2CV55440UT6646827")
                .then()
                .statusCode(200)
                .body(
                        "id", is("2CV55440UT6646827"),
                        "status", is("COMPLETED"),
                        "purchase_units[0].payments.captures[0].id", is("85W962518S850170V"),
                        "purchase_units[0].payments.captures[0].status", is("COMPLETED")
                );
    }

    @Test
    public void testOrderDetailsUnauthorizedException() {
        //when
        //token
        String jsonResp = TestUtil.getBody("response/paypal-token.json");
        Token token = Token.fromJsonString(jsonResp);
        Mockito.when(payPalService
                .getToken(payPalProperties.basicAuthorization(),
                        PayPalProperties.GET_TOKEN_DATA))
                .thenReturn(token);

        //order
        jsonResp = TestUtil.getBody("response/order-details.json");
        Order order = Order.fromJsonString(jsonResp);
        Mockito.when(payPalService
                .getOrder(payPalProperties.getAuthorization(token), "2CV55440UT6646827"))
                .thenThrow(UnauthorizedException.class)
                .thenReturn(order);

        //then
        given()
                .when().get("/v1/order/2CV55440UT6646827")
                .then()
                .statusCode(200)
                .body("id", is("2CV55440UT6646827"));
    }

    @Test
    public void testRefund() {
        //when
        //token
        String jsonResp = TestUtil.getBody("response/paypal-token.json");
        Token token = Token.fromJsonString(jsonResp);
        Mockito.when(payPalService
                .getToken(payPalProperties.basicAuthorization(),
                        PayPalProperties.GET_TOKEN_DATA))
                .thenReturn(token);
        //order
        jsonResp = TestUtil.getBody("response/capture-refund.json");
        RefundResponse refund = RefundResponse.fromJsonString(jsonResp);
        Mockito.when(payPalService
                .refund(payPalProperties.getAuthorization(token), "85W962518S850170V", null))
                .thenReturn(refund);
        //then
        given()
                .when().post("/v1/capture/refund/85W962518S850170V")
                .then()
                .statusCode(200)
                .body(
                        "id", is("020541522E438902X"),
                        "status", is("COMPLETED")
                );
    }

}