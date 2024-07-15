package com.example.mypet.pet.controller;

import com.example.mypet.pet.domain.dto.PetDto;
import com.example.mypet.pet.service.PetService;

import com.example.mypet.security.domain.users.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pets")
@Tag(name = "Pet Controller", description = "사용자 펫 관리 API" )
public class PetController {

    private final PetService petService;

    // 새로운 Pet 생성
    @PostMapping
    @Operation(summary = "새 펫 생성", description = "새로운 펫을 생성합니다.")
    public ResponseEntity<PetDto> createPet(@AuthenticationPrincipal Users user, @RequestBody PetDto petDto) {
        PetDto savedPet = petService.savePet(user, petDto);
        return ResponseEntity.ok(savedPet);
    }


}
