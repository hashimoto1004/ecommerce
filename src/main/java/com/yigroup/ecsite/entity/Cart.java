package com.yigroup.ecsite.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Cart {

    private int id;
    private int userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<CartItem> items = new ArrayList<>();

    // 表示用
    private List<CartItemForView> itemsForView;
    private int totalPrice;

    public void addItem(int productId, int variantId, int quantity) {
        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setStockId(variantId);
        item.setQuantity(quantity);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        this.items.add(item);
    }
}
