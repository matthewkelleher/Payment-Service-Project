package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("permitAll")
@RestController
public class AppController {

    @Autowired
    UserDao userDao;

@RequestMapping(path="/account/{username}")
    public BigDecimal balance(@PathVariable String username) { return userDao.getBalance(username);}

@RequestMapping(path="")
    public List<User> listOfUsers() { return userDao.findAll();}

    @RequestMapping(path="/{username}")
    public User findByUser(@PathVariable String username) { return userDao.findByUsername(username);}
}
