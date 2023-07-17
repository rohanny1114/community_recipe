package com.example.board.service;

import com.example.board.dao.UserDao;
import com.example.board.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;

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
        User tempUser = userDao.getUser(email); // Check if email is duplicated
        if(tempUser != null){
            throw new RuntimeException("This email is already joined");
        }
        User user = userDao.addUser(email, name, password);
        userDao.mapRole(user.getUserId()); // assign role
        return user;
        // End transaction here
    }

    // Get user information
    @Transactional
    public User getUser(String email){
        return userDao.getUser(email);
    }

    @Transactional(readOnly = true)
    public List<String> getRoles(int userId) {
        return userDao.getRoles(userId);
    }
}
