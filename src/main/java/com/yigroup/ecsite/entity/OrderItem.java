package com.yigroup.ecsite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderItem {

    private int id;
    private Order order;
    private Product product;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
