package ru.maxology.payments.paypal;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.SneakyThrows;


public class TestUtil {
    @SneakyThrows
    static String getBody(String path){
        return Resources.toString(Resources.getResource(path), Charsets.UTF_8);
    }
}
