package com.techelevator.tenmo.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransferTest {

    private Transfer testItem;

    @Before
    public void setup() {
        this.testItem = new Transfer(
         500, 4000, new BigDecimal (8888),"userFrom", "userTo", "type description",
         "status description");




    }

    @Test
    public void test_instantiate_transfer() {

        assertEquals(500, testItem.getAccount_from());
        assertEquals(4000, testItem.getAccount_to());
        assertEquals(new BigDecimal(8888), testItem.getAmount());
        assertEquals("userFrom", testItem.getUsernameFrom());
        assertEquals("userTo", testItem.getUsernameTo());
        assertEquals("type description", testItem.getTransfer_type_desc());
        assertEquals("status description", testItem.getTransfer_status_desc());
    }

}
