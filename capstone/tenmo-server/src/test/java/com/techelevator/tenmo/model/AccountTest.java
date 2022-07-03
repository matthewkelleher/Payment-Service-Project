package com.techelevator.tenmo.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {

    private Account testItem;

    @Before
    public void setup() {
        this.testItem = new Account("testID testID", "accountID", new BigDecimal("500"), "whatever" );
    }

    @Test
    public void test_instantiate_account() {

        assertEquals("testID testID", testItem.getUserId());
        assertEquals("accountID", testItem.getAccountId());
        assertEquals(new BigDecimal("500"), testItem.getBalance());
        assertEquals("whatever", testItem.getImage());
    }

}
