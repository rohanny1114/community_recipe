package com.example.board.service;


import com.example.board.dao.PostDao;
import com.example.board.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;

    @Transactional
    public void addPost(int userId, String title, String content) {
        postDao.addPost(userId, title, content);
    }
    @Transactional(readOnly = true) // SELECT only
    public int getTotalPosts() {
        return postDao.getTotalPosts();
    }
    @Transactional(readOnly = true)
    public List<Post> getPosts(int page) {
    return postDao.getPosts(page);
    }
}
