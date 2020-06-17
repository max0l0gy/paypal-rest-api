package ru.maxology.payments.paypal;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PayPalPropertiesTest {
    @Inject
    PayPalProperties payPalProperties;

    @Test
    public void testConfiguration() {
        assertEquals("https://api.sandbox.paypal.com", payPalProperties.sandbox);
    }

}
