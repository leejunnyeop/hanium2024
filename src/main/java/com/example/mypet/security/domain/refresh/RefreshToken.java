package com.example.mypet.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "refreshTokens")
public class RefreshToken {

    @Id
    private String id;
    private String email;
    private String token;

    public RefreshToken(String username, String token) {
        this.id = UUID.randomUUID().toString();
        this.email = username;
        this.token = token;
    }
    public RefreshToken withNewToken(String newToken) {
        return new RefreshToken(this.email, newToken);
    }

}
