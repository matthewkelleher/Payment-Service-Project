package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface TransferDao {

    List<Transfer> getTransferList(Integer id);

    Transfer mapRowToTransfer(SqlRowSet rs);
}
