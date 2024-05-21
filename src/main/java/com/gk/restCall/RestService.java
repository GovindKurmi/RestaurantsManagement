package com.gk.restCall;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestService {
    private final RestTemplate restTemplate;

    public <T, R> ResponseEntity<T> invokeRestCall(String url, HttpMethod method, HttpEntity<R> request, Class<T> responseType) {
        return restTemplate.exchange(url, method, request, responseType);
    }
}
