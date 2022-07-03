package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Account {
    @NotBlank
    private String userId;
    @NotBlank
    private String accountId;
    @NotBlank
    private BigDecimal balance;
    private String username;
    private String userImage;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account(String userId, String accountId, BigDecimal balance, String userImage) {
        this.userId = userId;
        this.accountId = accountId;
        this.balance = balance;
        this.userImage = userImage;
    }

    public String getImage() {
        return userImage;
    }

    public void setImage(String image) {
        this.userImage = image;
    }

    public Account() {

    }
}
