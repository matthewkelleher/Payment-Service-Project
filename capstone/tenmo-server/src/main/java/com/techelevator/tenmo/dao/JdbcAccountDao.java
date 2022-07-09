package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(DataSource ds) { this.jdbcTemplate = new JdbcTemplate(ds);}


    public Account getAccount(String username) {
        String sql = "SELECT * FROM account WHERE user_id =" +
                "(SELECT user_id FROM tenmo_user WHERE username ILIKE ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        Account account = new Account();
        if(results.next()) {
            account = mapRowToAccounts(results);
        }
        account.setUsername(username);
        return account;
    }

    public Account getImage(String username) {
        String sql = "SELECT userimage FROM account WHERE user_id =" +
                "(SELECT user_id FROM tenmo_user WHERE username ILIKE ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        Account account = new Account();
        if(results.next()) {
            account = mapRowToAccounts(results);
        }

        return account;
    }

    public Account setName(Account account) {
        String sql = "UPDATE account" +
                " SET firstname = ?" +
                " WHERE user_id = ?";
        jdbcTemplate.update(sql, account.getFirstName(), account.getUserId());

        String sql2 = "UPDATE account" +
                " SET lastname = ?" +
                " WHERE user_id = ?";
        jdbcTemplate.update(sql2, account.getLastName(), account.getUserId());

        return account;
    }





    private Account mapRowToAccounts(SqlRowSet rs) {

        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setUserId(rs.getLong("user_id"));
        account.setFirstName(rs.getString("firstname"));
        account.setLastName(rs.getString("lastname"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setImage(rs.getString("userimage"));

        return account;
    }


}
