package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

//@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RestController
public class AppController {

    @Autowired
    UserDao userDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    TransferDao transferDao;


    @ApiOperation(value = "List Users", notes = "This method lists users in the database")
    @RequestMapping(path = "")
    public List<User> listOfUsers() {
        return userDao.findAll();
    }

    @ApiOperation(value = "Get ID By Username", notes = "This method returns a numerical userID based on a Username")
    @GetMapping(path = "/id/{userName}")
    public long userID(@PathVariable String userName) {
        System.out.println(userName);
        return userDao.findByUsername(userName).getId();
    }

    @RequestMapping(path = "/account/balance")
    public Account getAnAccount(Principal principal) {

        return accountDao.getAccount(principal.getName());
    }

    @ApiOperation(value = "Bucks Send",
            notes = "This method initiates a transfer of funds between accounts")
    @RequestMapping(path = "/transfer", method = RequestMethod.PUT)
    public void bucksSend(@RequestBody Transfer transfer) {

        transferDao.sendBucks(transfer);

//       if(transfer.getAmount().compareTo(accountDao.getAccount(principal.getName()).getBalance()) <= 0 &&
//        transfer.getAccount_from() != transfer.getAccount_to()) {
//           transferDao.sendBucks(transfer);
//
//       } else if(transfer.getAmount().compareTo(accountDao.getAccount(principal.getName()).getBalance()) > 0) {
//
//          throw new ResponseStatusException(
//                  HttpStatus.I_AM_A_TEAPOT
//          );
//        } else if(transfer.getAccount_from() == transfer.getAccount_to()) {
//
//           throw new IllegalArgumentException();
//       }

    }

    @ApiOperation(value = "List Of Transfers",
            notes = "This method provides a list of all transfers in the current user's account")
    @RequestMapping(path = "/transfer", method = RequestMethod.GET)
    public List<Transfer> listOfTransfers(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return transferDao.getTransferList(principal.getName());
    }

    @ApiOperation(value = "Bucks Request",
            notes = "This method initiates a request for funds between accounts")
    @RequestMapping(path = "/request", method = RequestMethod.POST)
    public Transfer bucksRequest(@RequestBody Transfer transfer) {
        return transferDao.requestBucks(transfer);
    }

    @ApiOperation(value = "Transfers Pending",
            notes = "This method provides a list of pending transfers of which the user is a recipient")
    @RequestMapping(path = "/pending", method = RequestMethod.GET)
    public List<Transfer> transfersPending(Principal principal) {

        return transferDao.pendingTransfers(principal.getName());
    }

    @RequestMapping(path = "/approve", method = RequestMethod.PUT)
    public Transfer bucksApprove(@RequestBody Transfer transfer) {
        System.out.println(transfer);
        return transferDao.approveBucks(transfer);
    }

    @RequestMapping(path = "/reject", method = RequestMethod.PUT)
    public Transfer rejectTransfer(@RequestBody Transfer transfer) {
        return transferDao.rejectTransfer(transfer);
    }

    @ApiOperation(value = "List Of Users",
            notes = "This method provides a list of all users that match a search string")
    @RequestMapping(path = "/usernames/{username}", method = RequestMethod.GET)
    public List<User> listOfUsers(@PathVariable String username) {
//        int userId = userDao.findIdByUsername(principal.getName());
        return userDao.getUserNames(username);
    }

}
