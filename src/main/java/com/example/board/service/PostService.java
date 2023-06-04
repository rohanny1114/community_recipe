package com.example.board.service;


import com.example.board.dao.PostDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;

    @Transactional
    public void addPost(int userId, String title, String content) {
        postDao.addPost(userId, title, content);
    }
}
