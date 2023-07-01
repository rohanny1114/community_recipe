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

    @Transactional
    public Post getPost(int postId) {
        // Read post that matching the postId
        Post post = postDao.getPost(postId);
        // Increase 1 to the number of view
        postDao.updateView(postId);
        return  post;
    }

    /**
     * This method delete the selected post
     * when the id of the writer and the logged-in user is matched.
     * @param userId the id of the writer
     * @param postId the id of the post to be deleted
     */
    @Transactional
    public void deletePost(int userId, int postId) {
        Post post = postDao.getPost(postId);
        if(post.getUserId() == userId){
            postDao.deletePost(postId);
        }
    }
}
