package com.example.mypet.global.ex;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException{

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
