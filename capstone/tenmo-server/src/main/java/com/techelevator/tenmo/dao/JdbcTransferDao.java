package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.ProfileDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private ProfileDTO profileDTO = new ProfileDTO();
    public JdbcTransferDao(DataSource ds) { this.jdbcTemplate = new JdbcTemplate(ds);}


    public List<Transfer> getTransferList(String username) {
        String sql = "SELECT t.transfer_id, t.transfer_type_id, x.transfer_type_desc, t.transfer_status_id, y.transfer_status_desc, " +
                "t.account_from, t.account_to, t.amount, t.note, u.user_id, u.username AS name_to, b.user_id, b.username AS name_from " +
                "FROM transfer t " +
                "JOIN account a ON t.account_to = a.account_id " +
                "JOIN account f ON t.account_from = f.account_id " +
                "JOIN tenmo_user u ON a.user_id = u.user_id " +
                "JOIN tenmo_user b ON f.user_id = b.user_id " +
                "JOIN transfer_type x ON t.transfer_type_id = x.transfer_type_id " +
                "JOIN transfer_status y ON t.transfer_status_id = y.transfer_status_id " +
                "WHERE (b.username = ? OR u.username = ?) AND (t.transfer_status_id = 2 OR t.transfer_status_id = 3)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username, username);
        List<Transfer> transfer = new ArrayList<>();
        while(results.next()) {
            transfer.add(mapRowToTransfer(results));
        }
        return transfer;
    }



    public Transfer mapRowToTransfer(SqlRowSet rs) {

        Transfer transfer = new Transfer();
        transfer.setAccount_to(rs.getInt("account_to"));
        transfer.setAccount_from(rs.getInt("account_from"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        transfer.setTransfer_status_id(rs.getInt("transfer_status_id"));
        transfer.setTransfer_type_id(rs.getInt("transfer_type_id"));
        transfer.setUsernameFrom(rs.getString("name_from"));
        transfer.setTransfer_status_desc(rs.getString("transfer_status_desc"));
        transfer.setTransfer_type_desc(rs.getString("transfer_type_desc"));
        transfer.setTransfer_id(rs.getInt("transfer_id"));
        transfer.setUsernameTo(rs.getString("name_to"));
        transfer.setNote(rs.getString("note"));
        transfer.setNameFrom(getName(transfer.getAccount_from()));
        transfer.setNameTo(getName(transfer.getAccount_to()));

        return transfer;
    }

    public Transfer sendBucks(Transfer transfer) {

        String sql1 = "SELECT account_id FROM account WHERE user_id = ?";
        int senderId = jdbcTemplate.queryForObject(sql1, Integer.class, transfer.getAccount_from());
        int recipientId = jdbcTemplate.queryForObject(sql1, Integer.class, transfer.getAccount_to());
        String sql3 = "INSERT into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount, note) VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql3, 2, 2, senderId, recipientId, transfer.getAmount(), transfer.getNote());


        String sql = "UPDATE account" +
                " SET balance = balance - ?" +
                " WHERE user_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccount_from());

        String sql2 = "UPDATE account" +
                " SET balance = balance + ?" +
                " WHERE user_id = ?";
        jdbcTemplate.update(sql2, transfer.getAmount(), transfer.getAccount_to());




        return transfer;


    }

    public Transfer approveBucks(Transfer transfer) {

        String sql = "UPDATE account" +
                " SET balance = balance - ?" +
                " WHERE account_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccount_from());

        String sql2 = "UPDATE account" +
                " SET balance = balance + ?" +
                " WHERE account_id = ?";
        jdbcTemplate.update(sql2, transfer.getAmount(), transfer.getAccount_to());

        String sql3 = "UPDATE transfer" +
                " SET transfer_status_id = 2" +
                " WHERE transfer_id = ?";

        jdbcTemplate.update(sql3, transfer.getTransfer_id());



        return transfer;
    }

    public Transfer rejectTransfer(Transfer transfer) {
        String sql3 = "UPDATE transfer" +
                " SET transfer_status_id = 3" +
                " WHERE transfer_id = ?";

        jdbcTemplate.update(sql3, transfer.getTransfer_id());
        return transfer;
    }

    public Transfer requestBucks(Transfer transfer) {

        String sql1 = "SELECT account_id FROM account WHERE user_id = ?";
        int senderId = jdbcTemplate.queryForObject(sql1, Integer.class, transfer.getAccount_from());
        int recipientId = jdbcTemplate.queryForObject(sql1, Integer.class, transfer.getAccount_to());
        String sql3 = "INSERT into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount, note) VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql3, 1, 1, senderId , recipientId, transfer.getAmount(), transfer.getNote());

        return transfer;


    }

    public List<Transfer> pendingTransfers(String username) {
        String sql = "SELECT t.transfer_id, t.transfer_type_id, x.transfer_type_desc, t.transfer_status_id, t.note, y.transfer_status_desc, " +
                "t.account_from, t.account_to, t.amount, u.user_id, u.username AS name_to, b.user_id, b.username AS name_from FROM transfer t " +
                "JOIN account a ON t.account_to = a.account_id " +
                "JOIN account f ON t.account_from = f.account_id " +
                "JOIN tenmo_user u ON a.user_id = u.user_id " +
                "JOIN tenmo_user b ON f.user_id = b.user_id " +
                "JOIN transfer_type x ON t.transfer_type_id = x.transfer_type_id " +
                "JOIN transfer_status y ON t.transfer_status_id = y.transfer_status_id " +
                "WHERE (b.username = ? OR u.username = ?) AND t.transfer_status_id = 1";


        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username, username);
        List<Transfer> transfer = new ArrayList<>();
        while(results.next()) {
            transfer.add(mapRowToTransfer(results));
        }
        return transfer;

    }

    public String getName(long accountid) {
        ProfileDTO profileDTO = new ProfileDTO();
        String sql = "SELECT firstname, lastname FROM account " +
                "WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountid);
        if(results.next()) {
            profileDTO = mapRowToProfileDTO(results);
        }

        return profileDTO.getFirstName() + " " + profileDTO.getLastName();
    }

    private ProfileDTO mapRowToProfileDTO(SqlRowSet rs) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(rs.getString("firstname"));
        profileDTO.setLastName(rs.getString("lastname"));


        return profileDTO;

    }

}
