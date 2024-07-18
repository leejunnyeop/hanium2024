package com.example.mypet.pet.controller;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.example.mypet.pet.domain.dto.PetDto;
import com.example.mypet.pet.service.PetService;


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

@Tag(name = "pet", description = "사용자 펫 관리 API" )
public class PetController {

    private final PetService petService;

    // 새로운 Pets 생성
    @PostMapping
    @Operation(summary = "새 펫 생성", description = "새로운 펫을 생성합니다.")
    public ResponseEntity<PetDto> createPet(@AuthenticationPrincipal User user, @RequestBody PetDto petDto) {

        String userID = user.getUsername(); // id

        PetDto savedPet = petService.savePet(userID, petDto);
        return ResponseEntity.ok(savedPet);
    }

    // 사용자의 모든 Pets 조회
    @GetMapping
    @Operation(summary = "사용자의 모든 펫 조회", description = "현재 사용자에 속한 모든 펫을 조회합니다.")
    public ResponseEntity<List<PetDto>> getPetsByUser(@AuthenticationPrincipal User user) {
        String userID = user.getUsername(); // id
        List<PetDto> pets = petService.getPetsByUser(userID);
        return ResponseEntity.ok(pets);
    }

    // ID로 Pets 조회
    @GetMapping("/{petId}")
    @Operation(summary = "펫 조회", description = "ID를 사용하여 특정 펫을 조회합니다.")
    public ResponseEntity<PetDto> getPetById(@AuthenticationPrincipal User user, @PathVariable(name = "petId") String petId) {
        String userID = user.getUsername(); // id
        PetDto pet = petService.getPetById(userID, petId)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 " + petId + "인 펫을 찾을 수 없습니다."));
        return ResponseEntity.ok(pet);
    }


    // Pets 업데이트
    @PutMapping("/{petId}")
    @Operation(summary = "펫 업데이트", description = "ID를 사용하여 특정 펫을 업데이트합니다.")
    public ResponseEntity<PetDto> updatePet(@AuthenticationPrincipal User user, @PathVariable(name = "petId") String petId, @RequestBody PetDto petDto) {
        String userID = user.getUsername(); // id
        PetDto updatedPet = petService.updatePet(userID, petId, petDto);
        return ResponseEntity.ok(updatedPet);
    }

    // Pets 삭제
    @DeleteMapping("/{petId}")
    @Operation(summary = "펫 삭제", description = "ID를 사용하여 특정 펫을 삭제합니다.")
    public ResponseEntity<Void> deletePet(@AuthenticationPrincipal User user, @PathVariable(name = "petId") String petId) {
        String userID = user.getUsername(); // id
        petService.deletePet(userID, petId);
        return ResponseEntity.ok().build();
    }




}
