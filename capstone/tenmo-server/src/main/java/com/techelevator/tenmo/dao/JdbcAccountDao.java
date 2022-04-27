package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;



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

    public BigDecimal sendBucks (BigDecimal bucks, int sending_id, int receiving_id) {
        String sql = "UPDATE account" +
                "SET balance = balance - ?" +
                "WHERE user_id = ?";
        jdbcTemplate.update(sql, bucks, sending_id);

        String sql2 = "UPDATE account" +
                "SET balance = balance + ?" +
                "WHERE user_id = ?";
        jdbcTemplate.update(sql, bucks, receiving_id);


        return bucks;
    }

    private Account mapRowToAccounts(SqlRowSet rs) {

        Account account = new Account();
        account.setAccountId(rs.getString("account_id"));
        account.setUserId(rs.getString("user_id"));
        account.setBalance(rs.getString("balance"));

        return account;
    }
}
