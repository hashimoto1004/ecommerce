package com.yigroup.ecsite.repository;

import com.yigroup.ecsite.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(int id);

    void save(User user);

    void deleteById(int id);

    Optional<User> findByUsername(String username);
}
