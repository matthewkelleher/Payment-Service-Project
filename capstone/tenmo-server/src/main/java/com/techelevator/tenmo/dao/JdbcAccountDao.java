package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;


@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(DataSource ds) { this.jdbcTemplate = new JdbcTemplate(ds);}

//    public BigDecimal getBalance(String username) {
//        String sql = "SELECT balance FROM account WHERE account_id" +
//                " = (SELECT account_id FROM tenmo_user WHERE username ILIKE ?)";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
//        BigDecimal balance = new BigDecimal(0);
//        if (results.next()){
//            balance = results.getBigDecimal("balance");
//
//        }
//
//        return balance;
//    }

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

        String sql1 = "SELECT account_id FROM account WHERE user_id = ?";
        int senderId = jdbcTemplate.queryForObject(sql1, Integer.class, transfer.getAccount_from());
        int recipientId = jdbcTemplate.queryForObject(sql1, Integer.class, transfer.getAccount_to());
        String sql3 = "INSERT into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql3, 2, 2, senderId, recipientId, transfer.getAmount());

        String sql = "UPDATE account" +
                " SET balance = balance - ?" +
                " WHERE user_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccount_from());

        String sql2 = "UPDATE account" +
                " SET balance = balance + ?" +
                " WHERE user_id = ?";
        jdbcTemplate.update(sql2, transfer.getAmount(), transfer.getAccount_to());

        return transfer;

//        return transfer;
    }

    private Account mapRowToAccounts(SqlRowSet rs) {

        Account account = new Account();
        account.setAccountId(rs.getString("account_id"));
        account.setUserId(rs.getString("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));

        return account;
    }
}
