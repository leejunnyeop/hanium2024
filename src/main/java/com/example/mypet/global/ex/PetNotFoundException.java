package com.example.mypet.global.ex;

import org.springframework.http.HttpStatus;

public class PetNotFoundException extends BaseException{
    public PetNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
