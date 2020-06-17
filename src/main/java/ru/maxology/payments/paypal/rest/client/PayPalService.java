package ru.maxology.payments.paypal.rest.client;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import ru.maxology.payments.paypal.model.Token;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1")
@ApplicationScoped
@RegisterRestClient(configKey = "paypal-api")
@Produces(MediaType.APPLICATION_JSON)
public interface PayPalService {

    @POST
    @Path("/oauth2/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ClientHeaderParam(name = "Accept", value = MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Accept-Language", value = "en_US")
    Token token(@HeaderParam("Authorization") String authorization, String data);

}
