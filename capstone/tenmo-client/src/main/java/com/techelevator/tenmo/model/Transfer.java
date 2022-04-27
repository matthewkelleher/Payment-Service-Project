package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int account_from;
    private int account_to;
    private BigDecimal amount;

    public int getAccount_from() {
        return account_from;
    }

    public void setAccount_from(int account_from) {
        this.account_from = account_from;
    }

    public int getAccount_to() {
        return account_to;
    }

    public void setAccount_to(int account_to) {
        this.account_to = account_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transfer(int account_from, int account_to, BigDecimal amount) {
        this.account_from = account_from;
        this.account_to = account_to;
        this.amount = amount;
    }

    public Transfer() {

    }
}
