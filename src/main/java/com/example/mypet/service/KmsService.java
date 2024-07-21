package com.example.mypet.service;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Base64;

@Service
public class KmsService {

    @Value("${aws.kms.keyId}")
    private String keyId;

    private final AWSKMS kmsClient;

    public KmsService() {
        this.kmsClient = AWSKMSClientBuilder.defaultClient();
    }

    public String encrypt(String plaintext) {
        ByteBuffer plaintextBuffer = ByteBuffer.wrap(plaintext.getBytes());
        EncryptRequest req = new EncryptRequest().withKeyId(keyId).withPlaintext(plaintextBuffer);
        EncryptResult result = kmsClient.encrypt(req);
        ByteBuffer ciphertextBuffer = result.getCiphertextBlob();
        return Base64.getEncoder().encodeToString(ciphertextBuffer.array());
    }
    public String decrypt(String ciphertext) {

        try {
            ByteBuffer ciphertextBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(ciphertext));
            DecryptRequest req = new DecryptRequest().withCiphertextBlob(ciphertextBuffer);
            DecryptResult result = kmsClient.decrypt(req);
            ByteBuffer plaintextBuffer = result.getPlaintext();
            return new String(plaintextBuffer.array());
        } catch (Exception e) {
            throw new RuntimeException("KMS decryption failed", e);
        }
    }
}