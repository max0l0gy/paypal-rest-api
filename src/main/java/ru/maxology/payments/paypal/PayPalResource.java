package ru.maxology.payments.paypal;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import ru.maxology.payments.paypal.model.Order;
import ru.maxology.payments.paypal.model.RefundResponse;
import ru.maxology.payments.paypal.model.Token;
import ru.maxology.payments.paypal.rest.client.PayPalService;
import ru.maxology.payments.paypal.rest.client.UnauthorizedException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.function.Supplier;

@Slf4j
@Path("/v1")
public class PayPalResource {

    @Inject
    @RestClient
    PayPalService payPalService;
    @Inject
    PayPalProperties payPalProperties;

    @GET
    @Path("/token")
    @CacheResult(cacheName = "token")
    @Produces(MediaType.APPLICATION_JSON)
    public Token token() {
        log.info("get token from paypal");
        return payPalService
                .getToken(payPalProperties.basicAuthorization(),
                        PayPalProperties.GET_TOKEN_DATA);
    }

    @GET
    @Path("/order/{id}")
    @CacheResult(cacheName = "order")
    @Produces(MediaType.APPLICATION_JSON)
    public Order order(@PathParam(value = "id") String id) {
        return ifUnauthorizedRefreshToken(() -> payPalService.getOrder(payPalProperties.getAuthorization(token()), id));
    }

    @POST
    @Path("/capture/refund/{captureId}")
    @CacheResult(cacheName = "order")
    @Produces(MediaType.APPLICATION_JSON)
    public RefundResponse refund(@PathParam(value = "captureId") String id) {
        return ifUnauthorizedRefreshToken(() -> payPalService.refund(payPalProperties.getAuthorization(token()), id, null));
    }

    private <T> T ifUnauthorizedRefreshToken(Supplier<? extends T> supplier) {
        T value;
        try {
            value = supplier.get();
        } catch (UnauthorizedException ex) {
            invalidateToken();
            value = supplier.get();
        }
        return value;
    }

    @CacheInvalidate(cacheName = "token")
    public void invalidateToken() {
    }

}