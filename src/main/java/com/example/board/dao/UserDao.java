package com.example.board.dao;

import com.example.board.dto.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

// Only deals user data
@Repository
public class UserDao {
    // Create JDBC template
    private final NamedParameterJdbcTemplate jdbcTemplate;
    // Easy INSERT support
    private final SimpleJdbcInsertOperations insertUser;
    public UserDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertUser = new SimpleJdbcInsert(dataSource)
                        .withTableName("user")
                        .usingGeneratedKeyColumns("user_id"); // Auto-incremented user-id
    }

    /**
     * addUser set user information (email, name, password, user-id, data) and returns it.
     * user-id is auto-created, auto-increment number.
     * data is current local date using java.util.Data() method.
     *
     * @param email
     * @param name
     * @param password
     * @return
     */
    @Transactional
    public User addUser(String email, String name, String password){
        // INSERT INTO user (email, name, password, date) VALUES (?, ?, ?, now()); # user_id auto gen
        // SELECT LAST_INSERT_ID();

        // Bring user information
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setDate(LocalDateTime.now());

        // Bring user DTO into parameters.
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        // Execute INSERT using SimpleJdbcInsertOperations, and return auto-created user-id
        Number number =  insertUser.executeAndReturnKey(params);
        int userId = number.intValue();
        user.setUserId(userId);

        // return user information
        return user;
    }


    @Transactional
    public void mapRole(int userID){
        // INSERT INTO user_role (user_id, role_id) VALUES (?, ?);
        String sql = "INSERT INTO user_role (user_id, role_id) VALUES (:userId, 1)";
        // Map input userid as SQL userid value
        SqlParameterSource params = new MapSqlParameterSource("userId", userID);
        jdbcTemplate.update(sql, params);
    }
    // Sign in process
    @Transactional
    public User getUser(String email) {
        try {
            String sql = "SELECT user_id, email, name, password, date FROM user WHERE email = :email";
            SqlParameterSource params = new MapSqlParameterSource("email", email);
            RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
            User user = jdbcTemplate.queryForObject(sql, params, rowMapper);
            return user;
        } catch (Exception e){
            return null;
        }
    }
    // Get roles on the signed in account
    @Transactional(readOnly = true)
    public List<String> getRoles(int userId) {
        String sql = "SELECT r.name FROM user_role ur, role r WHERE ur.role_id = r.role_id AND ur.user_id = :userId";
        List<String> roles = jdbcTemplate.query(sql, Map.of("userId", userId), (rs, rowNum) -> {
           return rs.getString(1);
        });
        return  roles;
    }
}
