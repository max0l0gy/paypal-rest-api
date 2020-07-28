package ru.maxology.payments.paypal.rest.client;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import ru.maxology.payments.paypal.model.Order;
import ru.maxology.payments.paypal.model.RefundRequest;
import ru.maxology.payments.paypal.model.RefundResponse;
import ru.maxology.payments.paypal.model.Token;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@ApplicationScoped
@RegisterRestClient(configKey = "paypal-api")
@RegisterProvider(UnauthorizedExceptionMapper.class)
public interface PayPalService {

    @POST
    @Path("/v1/oauth2/token")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ClientHeaderParam(name = "Accept-Language", value = "en_US")
    @ClientHeaderParam(name = "Accept", value = MediaType.APPLICATION_JSON)
    Token getToken(@HeaderParam("Authorization") String authorization, String data);

    @GET
    @Path("/v2/checkout/orders/{id}")
    Order getOrder(@HeaderParam("Authorization") String authorization, @PathParam String id) throws UnauthorizedException;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/v2/payments/captures/{capture_id}/refund")
    RefundResponse refund(@HeaderParam("Authorization") String authorization, @PathParam("capture_id") String captureId, RefundRequest request);
}
