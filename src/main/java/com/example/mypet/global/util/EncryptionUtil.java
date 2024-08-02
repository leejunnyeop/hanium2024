package com.example.mypet.global.util;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EncryptionUtil {

    private static final String SECRET_KEY = UUID.randomUUID().toString();
    private static final String SALT = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

    private TextEncryptor encryptor;

    @PostConstruct
    public void init() {
        encryptor = Encryptors.text(SECRET_KEY, SALT);
    }

    public String encrypt(String text) {
        return encryptor.encrypt(text);
    }

    public String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}
