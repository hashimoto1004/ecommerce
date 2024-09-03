package com.yigroup.ecsite.repository;

import com.yigroup.ecsite.entity.OrderItem;
import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {

    List<OrderItem> findAll();

    Optional<OrderItem> findById(int id);

    void save(OrderItem orderItem);

    void deleteById(int id);
}
