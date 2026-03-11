package com.jmgs.worker.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class Utils {
    public static boolean isRetryableError(Throwable ex) {

        if (ex instanceof WebClientRequestException wex) {
            Throwable cause = wex.getCause();
            return cause instanceof IOException
                    || cause instanceof TimeoutException
                    || cause instanceof ConnectException; 
        }

        if (ex instanceof WebClientResponseException wex) {

            return wex.getStatusCode().is5xxServerError();
        }

        return ex instanceof IOException || ex instanceof TimeoutException;
    }
}
