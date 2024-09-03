package com.yigroup.ecsite.repository;

import java.util.Optional;

import com.yigroup.ecsite.entity.Cart;

public interface CartRepository {

    Optional<Cart> findByUserId(int userId);

    Optional<Cart> findCartItemsByUserId(int userId);

    void save(Cart cart);

    void delete(Cart cart);
}
