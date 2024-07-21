package com.example.mypet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PinataUploadService {


    private final RestTemplate restTemplate;

    @Value("${pinata.api.jwt}")
    private String pinataSecretApiKey;
    private static String pinataUploadUrl = "https://api.pinata.cloud/pinning/pinFileToIPFS";

    public ResponseEntity<String> uploadFile (String base64File, String userId) throws IOException {
        File file = convertBase64ToFile(base64File, userId);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ pinataSecretApiKey);
        headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
//        body.add("file", new FileSystemResource(file));
        body.add("file", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                pinataUploadUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        // 파일 삭제
        if (file.delete()) {
            System.out.println("Temp file deleted successfully");
        } else {
            System.out.println("Failed to delete the temp file");
        }
        return response;
    }


    // util
    private File convertBase64ToFile(String base64String, String filePath) throws IOException {
        // Base64 문자열을 디코딩하여 바이트 배열로 변환
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // 바이트 배열을 파일로 저장
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(decodedBytes);
        }
        return file;
    }
}
