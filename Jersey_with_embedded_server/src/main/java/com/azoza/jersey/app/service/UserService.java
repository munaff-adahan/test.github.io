package com.azoza.jersey.app.service;

import com.azoza.jersey.app.model.UserDetails;

public class UserService {
    public UserDetails getUserByEmail(String email){
        return new UserDetails("abc@gmail.com","1234");

    }
}
