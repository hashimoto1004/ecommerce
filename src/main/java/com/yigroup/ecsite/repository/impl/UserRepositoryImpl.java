package com.yigroup.ecsite.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yigroup.ecsite.entity.User;
import com.yigroup.ecsite.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[] { id }, new UserRowMapper())
                .stream()
                .findFirst();
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            // 新規作成の場合
            String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());
        } else {
            // 更新の場合
            String sql = "UPDATE users SET username = ?, email = ?, password = ?, role = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(),
                    user.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[] { username }, new UserRowMapper())
                .stream()
                .findFirst();
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            return user;
        }
    }
}
