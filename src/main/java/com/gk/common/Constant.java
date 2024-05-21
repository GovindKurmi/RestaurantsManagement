package com.gk.common;

public final class Constant {
    private static final String BASE_URL = "http://localhost:8093";
    public static final String CANCEL_URL = BASE_URL + "/payment/cancel";
    public static final String SUCCESS_URL = BASE_URL + "/order/placed";
    public static final String PAYPAL_URL = "https://api.sandbox.paypal.com/v2/checkout/orders";
}
