package com.example.mypet.global.ex;

import org.springframework.http.HttpStatus;

public class ServiceException extends BaseException{
    public ServiceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
