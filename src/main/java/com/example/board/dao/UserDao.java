package com.example.board.dao;

import com.example.board.dto.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// Only deals user data
@Repository
public class UserDao {
    @Transactional
    public User addUser(String email, String name, String password){
        // INSERT INTO user (email, name, password, date) VALUES (?, ?, ?, now()); # user_id auto gen
        // SELECT LAST_INSERT_ID();
        return null;
    }


    @Transactional
    public void mapRole(int userID){
        // INSERT INTO user_role (user_id, role_id) VALUES (?, ?);
    }


}
