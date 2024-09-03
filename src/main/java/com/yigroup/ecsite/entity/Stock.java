package com.yigroup.ecsite.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Stock {

    private int id;
    private int productId;
    private int sizeId;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private double sizeValue;
}
