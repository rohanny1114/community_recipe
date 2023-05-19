package com.example.board.service;

import com.example.board.dao.UserDao;
import com.example.board.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

// class that handle business in transaction unit
@Service
@RequiredArgsConstructor
// public UserService (UserDao userDao){this.userDao = userDao;}
//  makedIt makes Construction injection: Spring creates bean for UserService in constructor
public class UserService {
    private final UserDao userDao;

    // User: Read user info in the DB
    @Transactional // make multiple action into one transaction
    public User addUser(String name, String email, String password) {
        User user = userDao.addUser(email, name, password);
        userDao.mapRole(user.getUserId()); // assign role
        return user;
    }

}
