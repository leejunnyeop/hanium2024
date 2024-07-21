package com.example.mypet.controller;

import com.example.mypet.dto.PinataResponse;
import com.example.mypet.service.PinataUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/pinata")
@RequiredArgsConstructor
@Tag(name = "pinata", description = "Pinata Cloud Upload API")
public class PinataController {


    private final PinataUploadService pinataUploadService;

    @Operation(summary = "pinata 이미지 업로드", description = "pinata에 이미지 업로드", responses = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content (mediaType = "application/json", schema = @Schema(implementation = PinataResponse.class)))
    })
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@AuthenticationPrincipal User user, @RequestParam("file") String base64File) throws IOException {
        return pinataUploadService.uploadFile(base64File, user.getUsername());
    }


}
