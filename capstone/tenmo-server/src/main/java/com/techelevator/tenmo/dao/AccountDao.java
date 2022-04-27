package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    Account getAccount(String username);

    BigDecimal sendBucks (BigDecimal bucks, int sending_id, int receiving_id);

}
