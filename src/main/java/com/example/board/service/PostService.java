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
        return getPost(postId, true);
    }

    /**
     * This method get the chosen post to update the content
     * The view count will not be increased if updateViewCnt is false.
     * @param postId the chosen post id
     * @param updateViewCnt
     * @return
     */
    @Transactional
    public Post getPost(int postId, boolean updateViewCnt){
        // Read post that matching the postId
        Post post = postDao.getPost(postId);
        if(updateViewCnt){
            // Increase 1 to the number of view
            postDao.updateView(postId);
        }
        return post;
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
    @Transactional
    public void updatePost(int postId, String title, String content) {
        postDao.updatePost(postId, title, content);
    }
}
