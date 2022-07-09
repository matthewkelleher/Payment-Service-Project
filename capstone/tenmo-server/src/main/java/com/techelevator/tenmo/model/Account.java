package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Account {
    @NotBlank
    private long userId;
    @NotBlank
    private long accountId;
    @NotBlank
    private BigDecimal balance;
    @NotBlank
    private String username;
    private String userImage;
    private String firstName;
    private String lastName;

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account(long userId, long accountId, BigDecimal balance, String firstname, String lastname, String userImage) {
        this.userId = userId;
        this.accountId = accountId;
        this.balance = balance;
        this.userImage = userImage;
        this.lastName = lastname;
        this.firstName = firstname;
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
