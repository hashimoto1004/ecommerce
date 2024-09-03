package com.yigroup.ecsite.entity;

import lombok.Data;

@Data
public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
}
