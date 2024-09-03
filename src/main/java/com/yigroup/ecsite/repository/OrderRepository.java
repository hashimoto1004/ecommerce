package com.yigroup.ecsite.repository;

import com.yigroup.ecsite.entity.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAll();

    Optional<Order> findById(int id);

    void save(Order order);

    void deleteById(int id);
}
