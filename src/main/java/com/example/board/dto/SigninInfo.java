package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
//@AllArgsConstructor
public class SigninInfo {
    private int userId;
    private String email;
    private String name;
    private List<String> roles = new ArrayList<>();

    public SigninInfo(int userId, String email, String name){
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
}
