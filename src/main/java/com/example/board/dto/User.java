package com.example.board.dto;

import com.example.board.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    private int userId;
    private String email;
    private String name;
    private String password;
    private LocalDateTime date; //원래는 날짜로 가져와서 문자로 처리

    // user_id, int, NO, PRIMARY KEY, auto_increment
    // email, VARCHAR(255)
    // name, VARCHAR(50)
    // password, VARCHAR(500)
    // date, timestamp, current_timestamp, default_generated
}
