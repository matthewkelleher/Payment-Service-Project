package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public BigDecimal getBalance(String username) {
        String sql = "SELECT balance FROM account WHERE account_id" +
                " = (SELECT account_id FROM tenmo_user WHERE username ILIKE ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        BigDecimal balance = new BigDecimal(0);
        if (results.next()){
            balance = results.getBigDecimal("balance");

        }

        return balance;
    }

    public Account getAccount(String username) {
        String sql = "SELECT * FROM account WHERE user_id =" +
                "(SELECT user_id FROM tenmo_user WHERE username ILIKE ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        Account account = new Account();
        while(results.next()) {
            account = mapRowToAccounts(results);
        }
        return account;
    }

    public Transfer sendBucks (Transfer transfer) {
        String sql = "UPDATE account" +
                "SET balance = balance - ?" +
                "WHERE user_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccount_from());

        String sql2 = "UPDATE account" +
                "SET balance = balance + ?" +
                "WHERE user_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccount_to());


        return transfer;
    }

    private Account mapRowToAccounts(SqlRowSet rs) {

        Account account = new Account();
        account.setAccountId(rs.getString("account_id"));
        account.setUserId(rs.getString("user_id"));
        account.setBalance(rs.getString("balance"));

        return account;
    }
}
