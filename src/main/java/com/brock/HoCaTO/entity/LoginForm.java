package com.brock.HoCaTO.entity;

import lombok.Data;

@Data
public class LoginForm {
    private String email;
    private String password;

    //Probably need to add database annotations
    //See Role or User
}
