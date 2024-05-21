package com.gk.restCall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.common.Constant;
import com.gk.dto.Order;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RestServiceCall {
    private final APIContext apiContext;
    private final RestService restService;
    private final ObjectMapper mapper;

    public String createPayment(Order order) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        String requestJson = mapper.writeValueAsString(order);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("Basic " + encodeCredentials(apiContext.getClientID(), apiContext.getClientSecret()));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<Payment> paymentResponseEntity = restService.invokeRestCall(Constant.PAYPAL_URL, HttpMethod.POST, entity, Payment.class);
        Payment payment1 = paymentResponseEntity.getBody();
        return payment1.getState();
    }

    private String encodeCredentials(String clientId, String clientSecret) {
        String credentials = clientId + ":" + clientSecret;
        return new String(Base64.getEncoder().encodeToString(credentials.getBytes()));
    }

}
