package com.example.board.dao;

import com.example.board.dto.Post;
import com.example.board.dto.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class PostDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insertPost;

    public PostDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertPost = new SimpleJdbcInsert(dataSource)
                .withTableName("post")
                .usingGeneratedKeyColumns("post_id"); // Auto-incremented post_id
    }

    @Transactional
    public void addPost(int userId, String title, String content){
        // Bring user information
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setContent(content);
        post.setDate(LocalDateTime.now());

        // Bring user DTO into parameters.
        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        insertPost.execute(params);
    }
    @Transactional(readOnly = true)
    public int getTotalPosts() {
        String sql = "SELECT COUNT(*) AS total_posts FROM post";
        Integer totalPosts = jdbcTemplate.queryForObject(sql, Map.of(), Integer.class);
        return totalPosts.intValue();

    }
    @Transactional(readOnly = true)
    public List<Post> getPosts(int page) {
        int start = (page - 1 ) * 10;
        String sql = "SELECT p.user_id, p.post_id, p.title, p.date, p.view, u.name FROM post p, user u WHERE p.user_id = u.user_id ORDER BY post_id DESC LIMIT :start,10";
        RowMapper<Post> rowMapper = BeanPropertyRowMapper.newInstance(Post.class);
        List<Post> posts = jdbcTemplate.query(sql, Map.of("start", start), rowMapper);
        return posts;
    }
}
