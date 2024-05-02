package com.rentitnow.facade;

public class RentProcessingException extends Exception {

    public static String ERR_PAYMENT_REJECTION = "Payment was rejected";

    public RentProcessingException(String message) {
        super(message);
    }
}
