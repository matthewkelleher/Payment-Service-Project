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
        if(results.next()) {
            account = mapRowToAccounts(results);
        }
        account.setUsername(username);
        return account;
    }








    private Account mapRowToAccounts(SqlRowSet rs) {

        Account account = new Account();
        account.setAccountId(rs.getString("account_id"));
        account.setUserId(rs.getString("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setImage(rs.getString("userImage"));

        return account;
    }


}
