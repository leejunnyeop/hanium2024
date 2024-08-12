package com.example.mypet.service;

import com.amazonaws.services.s3.AmazonS3;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Log4j2
public class S3Service  {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public String upload(MultipartFile multipartFile) {
        String imageUrl;

        var fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        try(InputStream inputStream = multipartFile.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imageUrl = s3Client.getUrl(bucketName, fileName).toString();
        } catch(IOException e) {
            throw new RuntimeException("이미지 업로드 에러");
        }

//        log.info(imgUrlList);
        return imageUrl;
    }

    public List<String> upload(List<String> base64Strings) throws IOException {
        List<String> imgUrlList = new ArrayList<>();
        var multipartFileList =convertBase64ToMultipartFile(base64Strings);
        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        for (MultipartFile file : multipartFileList) {
            String fileName = file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            //S3로 putObject 할 때 사용할 요청 객체
            //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
            try(InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgUrlList.add(s3Client.getUrl(bucketName, fileName).toString());
            } catch(IOException e) {
                throw new RuntimeException("이미지 업로드 에러");
            }
        }

        return imgUrlList;
    }


    // 이미지파일명 중복 방지
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 유효성 검사
    // useless
    private String getFileExtension(String fileName) {
        if (fileName.isEmpty()) {
            throw new RuntimeException("이미지 업로드 에러");

        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new RuntimeException("이미지 업로드 에러");

        }
        return fileName.substring(fileName.lastIndexOf("."));
    }


    public List<MultipartFile> convertBase64ToMultipartFile(List<String> base64Strings) throws IOException {
        List<MultipartFile> multipartFiles = new ArrayList<>();

        for (String base64String : base64Strings) {

            // Base64 문자열을 디코딩하여 바이트 배열로 변환
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);

            // UUID로 파일 이름 생성하고 확장자 추가
            String fileName = UUID.randomUUID().toString() + ".png";

            // MultipartFile 생성
            MultipartFile multipartFile = new MockMultipartFile(
                    fileName,
                    fileName,
                    "image/png",
                    new ByteArrayInputStream(decodedBytes) // 바이트 배열을 InputStream으로 변환
            );

            // 리스트에 추가
            multipartFiles.add(multipartFile);
        }

        return multipartFiles;
    }

    private MultipartFile convertBase64ToMultipartFile(String base64String, String fileName) throws IOException {
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
