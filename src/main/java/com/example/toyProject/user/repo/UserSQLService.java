package com.example.toyProject.user.repo;

import com.example.toyProject.user.DTO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class UserSQLService implements UserDatabaseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public boolean saveUser(User user) {
        final String sql = "INSERT INTO USERS(USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[] {user.getUsername(), user.getPassword(), user.getEmail()}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR}) > 0;
    }

    public User fetchUser(String username) {
        final String sql = "SELECT USERNAME, PASSWORD, EMAIL FROM USERS WHERE USERNAME = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {username}, new int[] {Types.VARCHAR}, new BeanPropertyRowMapper<>(User.class));
    }
}
