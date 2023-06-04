package com.example.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.security.PrivateKey;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {
    private int postId;
    private String title;
    private String content;
    private String name;
    private int userId;
    private LocalDateTime date;
    private int view;
}
