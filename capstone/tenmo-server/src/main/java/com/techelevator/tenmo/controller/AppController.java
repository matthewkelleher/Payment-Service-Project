package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AppController {

    @Autowired
    UserDao userDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    TransferDao transferDao;

//    @RequestMapping(path="/account/{username}")
//    public BigDecimal balance(@PathVariable String username) { return userDao.getBalance(username);}

    @RequestMapping(path="")
    public List<User> listOfUsers() { return userDao.findAll();}

    @RequestMapping(path="/{username}")
    public User findByUser(@PathVariable String username) { return userDao.findByUsername(username);}

    @RequestMapping(path="/account/{username}") // get username out of here
    public Account getAnAccount(@PathVariable String username) {return accountDao.getAccount(username);}

    @RequestMapping(path="/transfer", method = RequestMethod.PUT)
    public Transfer bucksSend(@RequestBody Transfer transfer) {return transferDao.sendBucks(transfer);}

    @RequestMapping(path="/transfer/{id}", method = RequestMethod.GET) // get username out of here too
    public List<Transfer> listOfTransfers(@PathVariable Integer id) {return transferDao.getTransferList(id);}
    @RequestMapping(path="/request", method = RequestMethod.POST)
    public Transfer bucksRequest(@RequestBody Transfer transfer) {return transferDao.requestBucks(transfer);}
    @RequestMapping(path="/pending", method = RequestMethod.GET)
    public List<Transfer> listOfTransfers(Principal principal) {

        return transferDao.pendingTransfers(principal.getName());}
}
