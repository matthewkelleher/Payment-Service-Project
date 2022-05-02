package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Transfer {

    private int transfer_id;
    private int transfer_type_id;
    private int transfer_status_id;
    @NotBlank
    private int account_from;
    @NotBlank
    private int account_to;
    @NotBlank
    private BigDecimal amount;
    private String usernameFrom;
    private String transfer_type_desc;
    private String transfer_status_desc;
    private String usernameTo;

    public String getUsernameTo() {
        return usernameTo;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getTransfer_type_id() {
        return transfer_type_id;
    }

    public void setTransfer_type_id(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

    public int getTransfer_status_id() {
        return transfer_status_id;
    }

    public void setTransfer_status_id(int transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
    }

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

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public void setUsernameFrom(String usernameFrom) {
        this.usernameFrom = usernameFrom;
    }



    public Transfer() {
    }

    public String getTransfer_type_desc() {
        return transfer_type_desc;
    }

    public void setTransfer_type_desc(String transfer_type_desc) {
        this.transfer_type_desc = transfer_type_desc;
    }

    public String getTransfer_status_desc() {
        return transfer_status_desc;
    }

    public void setTransfer_status_desc(String transfer_status_desc) {
        this.transfer_status_desc = transfer_status_desc;
    }

    public Transfer(int account_from,
                    int account_to, BigDecimal amount, String usernameFrom, String usernameTo, String transfer_type_desc, String transfer_status_desc) {
        this.transfer_id = transfer_id;
        this.transfer_type_id = transfer_type_id;
        this.transfer_status_id = transfer_status_id;
        this.account_from = account_from;
        this.account_to = account_to;
        this.amount = amount;
        this.usernameFrom = usernameFrom;
        this.transfer_type_desc = transfer_type_desc;
        this.transfer_status_desc = transfer_status_desc;
        this.usernameTo = usernameTo;


    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transfer_id=" + transfer_id +
                ", transfer_type_id=" + transfer_type_id +
                ", transfer_status_id=" + transfer_status_id +
                ", account_from=" + account_from +
                ", account_to=" + account_to +
                ", amount=" + amount +
                ", usernameFrom='" + usernameFrom + '\'' +
                ", transfer_type_desc='" + transfer_type_desc + '\'' +
                ", transfer_status_desc='" + transfer_status_desc + '\'' +
                ", usernameTo='" + usernameTo + '\'' +
                '}';
    }
}
