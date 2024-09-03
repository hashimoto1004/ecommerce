package com.yigroup.ecsite.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CartItem {

    private int id;
    private int cartId;
    private int productId;
    private int stockId;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
