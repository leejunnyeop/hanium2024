package com.example.mypet.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.security.SecureRandom;

// wallet generate
@Service
public class WalletService {

    public Wallet createWallet() throws Exception {
        SecureRandom random = new SecureRandom();
        ECKeyPair keyPair = Keys.createEcKeyPair(random);
        String address = "0x" + Keys.getAddress(keyPair);
        String privateKey = keyPair.getPrivateKey().toString(16);
        String publicKey = keyPair.getPublicKey().toString(16);

        Wallet wallet = new Wallet(address, publicKey, privateKey);
        return wallet;
    }

    @Getter
    public static class Wallet {
        private String address;
        private String publicKey;
        private String privateKey;

        public Wallet(String address, String publicKey, String privateKey) {
            this.address = address;
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

    }
}
