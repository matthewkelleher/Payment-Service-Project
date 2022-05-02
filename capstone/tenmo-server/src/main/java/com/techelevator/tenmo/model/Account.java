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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Account(String userId, String accountId, BigDecimal balance) {
        this.userId = userId;
        this.accountId = accountId;
        this.balance = balance;
    }

    public Account() {

    }
}
