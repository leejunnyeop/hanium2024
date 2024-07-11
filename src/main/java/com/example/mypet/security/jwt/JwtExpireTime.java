package com.example.mypet.security.jwt;

public class JwtExpireTime {

    public static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L; // 30일

    public static final long REFRESH_TOKEN_EXPIRE_TIME = 90 * 24 * 60 * 60 * 1000L; //90일

//    public static final long ACCESS_TOKEN_EXPIRE_TIME = 30000;               //30분


}

