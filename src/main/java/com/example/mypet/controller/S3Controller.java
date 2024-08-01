package com.example.mypet.controller;

import com.example.mypet.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
@Tag(name = "s3", description = "s3에 이미지 업로드")
public class S3Controller {

    private final S3Service s3Service;

    // 혹시 몰라서 배열로 여러 이미지 받을 수 있도록 설정
    @Operation(summary = "이미지 업로드", description = "이미지를 s3에 업로드 합니다.")
    @PostMapping
    public ResponseEntity<List<String>> uploadFile(@RequestParam("file") List<MultipartFile> multipartFile) throws IOException {
        return new ResponseEntity<>(s3Service.upload(multipartFile), HttpStatus.OK) ;
    }
}