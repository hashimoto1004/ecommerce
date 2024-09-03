package com.yigroup.ecsite.entity;

import lombok.Data;

@Data
public class CartItemForView {
    private int id;
    private int cartId;
    private String productName;
    private double size;
    private int price;
    private String imageUrl;
    private int quantity;
    private int subTotalPrice;
}
