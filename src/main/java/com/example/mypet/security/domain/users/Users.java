package com.example.mypet.security.domain.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Document(collation = "users")
@AllArgsConstructor
public class Users {

    @Id
    private String id;

    private String username;
    private String email;
    private String provider;

    public static Users createUser(String email, String name, String provider) {
        return new Users(UUID.randomUUID().toString(), name, email, provider);
    }
}
