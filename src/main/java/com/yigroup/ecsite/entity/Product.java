package com.yigroup.ecsite.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Product {

    private int id;
    private String name;
    private String description;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<Stock> stocks;
    private List<Image> images;
}
