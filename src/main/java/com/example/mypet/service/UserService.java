package com.example.mypet.service;

import com.example.mypet.enums.Role;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final KmsService kmsService;
    private final WalletService walletService;
    public Users registerMember(JSONObject json, String authProvider) {

        // 지갑 생성
        WalletService.Wallet wallet = null;
        try {
            wallet = walletService.createWallet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // kms 를 통한 암호화
        String encryptedPrivateKey = kmsService.encrypt(wallet.getPrivateKey());

        Users user = Users.builder()
                .provider(authProvider)
                .role(Role.USER.name())
                .email(json.getString("email"))
                .name(json.getString("name"))
                // setting crypto wallet
                .address(wallet.getAddress())
                .publicKey(wallet.getPublicKey())
                .encryptedPrivateKey(encryptedPrivateKey)
                .build();
        return usersRepository.save(user);
    }
}
