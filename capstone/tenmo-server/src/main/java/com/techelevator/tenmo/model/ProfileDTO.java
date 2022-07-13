package com.techelevator.tenmo.model;

public class ProfileDTO {

    private long accountId;
    private String firstName;
    private String lastName;

    public ProfileDTO() {

    }

    public ProfileDTO(long accountId, String firstName, String lastName) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getUserId() {
        return accountId;
    }

    public void setUserId(long userId) {
        this.accountId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
