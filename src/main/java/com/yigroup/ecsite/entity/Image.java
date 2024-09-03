package com.yigroup.ecsite.entity;

import lombok.Data;

@Data
public class Image {

    private int id;
    private int productId;
    private String imageUrl;
    private String altText;
}
