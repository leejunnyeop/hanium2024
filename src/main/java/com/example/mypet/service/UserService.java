package com.example.mypet.service;

import com.example.mypet.enums.Role;

import com.example.mypet.security.domain.users.UserProfileRequest;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONObject;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final KmsService kmsService;
    private final WalletService walletService;
    private final S3Service s3Service;

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

    @Transactional
    public void updateUserProfile(String userId, UserProfileRequest userProfileRequest) throws IOException {
        MultipartFile multipartFile;
        String imageUrl = null;
        if (userProfileRequest.getBase64ProfileImage() != null && !userProfileRequest.getBase64ProfileImage().isEmpty()) {
            multipartFile = convertBase64ToMultipartFile(userProfileRequest.getBase64ProfileImage(), userId);
            imageUrl = s3Service.upload(multipartFile);

        }
        try {
            Users users = usersRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 정보를 확인 해주세요"));
            userProfileRequest.setProfileImageUrl(imageUrl);
            users.userProfileUpdate(userProfileRequest);
            usersRepository.save(users);
        } catch (Exception e) {
            throw new IllegalArgumentException("유저 정보가 확인 안됩니다 " + e.getMessage());
        }
    }


    public MultipartFile convertBase64ToMultipartFile(String base64String, String fileName) throws IOException {
        // Base64 문자열을 디코딩하여 바이트 배열로 변환
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // MultipartFile 생성
        return new MockMultipartFile(
                fileName,                          // 파일 이름
                fileName,                          // 원래 파일 이름
                "application/octet-stream",        // MIME 타입 (필요에 따라 변경 가능)
                new ByteArrayInputStream(decodedBytes) // 바이트 배열을 InputStream으로 변환
        );
    }
}
