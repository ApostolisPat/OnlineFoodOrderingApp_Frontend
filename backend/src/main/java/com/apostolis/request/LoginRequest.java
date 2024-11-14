package com.apostolis.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}

/*
 * Example JSON Body
{
    "email":"demo2@demo.com",
    "password":"123456789"
}
 */