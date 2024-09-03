package com.yigroup.ecsite.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {

    private int id;
    private LocalDateTime orderDate;
    private User user;
    private List<OrderItem> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
