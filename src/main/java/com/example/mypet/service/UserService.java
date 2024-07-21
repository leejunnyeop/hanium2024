package com.example.mypet.service;

import com.example.mypet.enums.Role;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final KmsService kmsService;
    private final WalletService walletService;


    @Transactional(readOnly = true)
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



    @Transactional(readOnly = true)
    public Users getUserInfo(String userId){
        // dto 고려
        var user =  usersRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        return user;
    }

    @Transactional(readOnly = true)
    public Users changeTermsOfServiceAgreement(String userId){
        var user = usersRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        user.setTermsOfServiceAgreement();
        usersRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public String decodeToken(String userId){
        var user = usersRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        return kmsService.decrypt(user.getEncryptedPrivateKey());
    }
}
