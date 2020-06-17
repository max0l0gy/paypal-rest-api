package ru.maxology.payments.paypal;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.maxology.payments.paypal.model.Token;
import ru.maxology.payments.paypal.rest.client.PayPalService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1")
public class PayPalResource {

    @Inject
    @RestClient
    PayPalService payPalService;
    @Inject
    PayPalProperties payPalProperties;

    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Token token() {
        return payPalService
                .token(payPalProperties.basicAuthorization(),
                        PayPalProperties.GET_TOKEN_DATA);
    }

}