package com.yigroup.ecsite.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Size {

    private int id;
    private double value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
