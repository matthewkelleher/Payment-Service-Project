package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class TenmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;



    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) { // variable forms request body

        HttpHeaders headers = new HttpHeaders(); // we need headers for request
        headers.setBearerAuth(this.authToken); // add auth line to header - either add provided token or null

        return new HttpEntity<>(transfer, headers);

    }

    private HttpEntity<Void> makeAuthEntity() {

        HttpHeaders headers = new HttpHeaders(); // we need headers for request
//        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(this.authToken); // add auth line to header - either add provided token or null

        return new HttpEntity<>(headers);
    }

    public boolean userList() {


        try {
            List<User> listUsers = List.of(restTemplate.exchange(
                    API_BASE_URL,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    User[].class
            ).getBody());

            for (User user : listUsers) {
                System.out.println(user.getId() + " : " + user.getUsername());
            }
        } catch (RestClientException e) {
            System.out.println("Something went wrong.");
        }
        return true;
    }


    public Account getAccount(String username) {


        return restTemplate.exchange(
        API_BASE_URL + "account/" + username, // get username out of path
        HttpMethod.GET,
        makeAuthEntity(),
        Account.class
            ).getBody();
    }

    public List<Transfer> listOfTransfers (Integer id) {

        List<Transfer> transferList = List.of(
                restTemplate.exchange(
                API_BASE_URL + "transfer/" + id, // get username out of path
                HttpMethod.GET,
                makeAuthEntity(),
                Transfer[].class
        ).getBody());

        return transferList;
    }

    public Transfer transferBucks(Transfer transfer) {


        return restTemplate.exchange(
                API_BASE_URL + "transfer",
                HttpMethod.PUT,
                makeTransferEntity(transfer),
                Transfer.class
        ).getBody();








    }



}
