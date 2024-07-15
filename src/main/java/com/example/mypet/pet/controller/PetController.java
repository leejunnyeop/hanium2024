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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 사용자의 모든 Pet 조회
    @GetMapping
    @Operation(summary = "사용자의 모든 펫 조회", description = "현재 사용자에 속한 모든 펫을 조회합니다.")
    public ResponseEntity<List<PetDto>> getPetsByUser(@AuthenticationPrincipal Users user) {
        List<PetDto> pets = petService.getPetsByUser(user);
        return ResponseEntity.ok(pets);
    }





}
