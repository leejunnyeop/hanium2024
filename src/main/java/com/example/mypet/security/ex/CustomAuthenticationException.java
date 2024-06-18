package com.example.mypet.security.handler;

public class CustomAuthenticationException extends AuthenticationException{
    public CustomAuthenticationException(String message) {
        super(message);
    }

}
