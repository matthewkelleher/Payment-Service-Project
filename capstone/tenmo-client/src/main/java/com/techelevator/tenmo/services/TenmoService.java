package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TenmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private HttpEntity<Void> makeAuthEntity() {

        HttpHeaders headers = new HttpHeaders(); // we need headers for request
        headers.setBearerAuth(this.authToken); // add auth line to header - either add provided token or null
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return entity;
    }




    public Account getAccount(String username) {


        return restTemplate.exchange(
        "https://localhost:8080/account/" + username,
        HttpMethod.GET,
        makeAuthEntity(),
        Account.class
            ).getBody();
    }

}
