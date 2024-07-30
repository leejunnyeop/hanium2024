package com.example.mypet.controller;

import com.example.mypet.service.S3Service;
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
@RequestMapping("/admin/upload")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    // todo
    @PostMapping
    public ResponseEntity<List<String>> uploadFile(@RequestParam("file") List<MultipartFile> multipartFile) throws IOException {
        return new ResponseEntity<>(s3Service.upload(multipartFile), HttpStatus.OK) ;
    }
}