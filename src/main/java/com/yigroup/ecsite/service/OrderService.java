package com.yigroup.ecsite.service;

import com.yigroup.ecsite.entity.Order;
import com.yigroup.ecsite.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    // private final OrderRepository orderRepository;

    // public OrderService(OrderRepository orderRepository) {
    // this.orderRepository = orderRepository;
    // }

    public List<Order> getAllOrders() {
        // return orderRepository.findAll();
        return null;
    }

    public Optional<Order> getOrderById(int id) {
        // return orderRepository.findById(id);
        return null;
    }

    public void createOrder(Order order) {
        // orderRepository.save(order);
    }

    public void cancelOrder(int id) {
        // orderRepository.deleteById(id);
    }
}
