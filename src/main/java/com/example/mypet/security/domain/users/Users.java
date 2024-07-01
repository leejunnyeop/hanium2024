package com.example.mypet.security.domain.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@Document(collation = "users")
public class Users {

    @Id
    private String id;
    private String email;
    private String name;
    private String provider;
    private String refreshToken;
    private String address;
    private String publicKey;
    private String encryptedPrivateKey;

    public static Users createUser(String email, String name, String provider, String address,
                                   String publicKey, String encryptedPrivateKey) {
        return Users.builder()
                .email(email)
                .name(name)
                .provider(provider)
                .address(address)
                .publicKey(publicKey)
                .encryptedPrivateKey(encryptedPrivateKey)
                .build();
    }




}
