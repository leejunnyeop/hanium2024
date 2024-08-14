package com.example.mypet.pet.controller;


import com.example.mypet.pet.domain.dto.PetRequestDto;
import com.example.mypet.pet.domain.dto.PetResponseDto;
import com.example.mypet.pet.domain.entity.Pets;
import com.example.mypet.pet.service.PetService;


import com.example.mypet.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;



@RequiredArgsConstructor
@RestController
@RequestMapping("/pets")
@Tag(name = "pet", description = "사용자 펫 관리 API" )
public class PetController {

    private final PetService petService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "펫 등록", description = "새로운 반려동물을 등록합니다.")
    public ResponseEntity<String> createPet(@AuthenticationPrincipal User user, @Valid @RequestBody PetRequestDto petRequestDto) throws IOException {
        String userId = user.getUsername(); // 사용자 ID
        petService.savePet(userId, petRequestDto);
        return ResponseEntity.ok("강아지 등록이 완료되었습니다.");
    }

    @GetMapping("/{petId}")
    @Operation(summary = "펫 조회", description = "첫번째 반려 동물 조회")
    public ResponseEntity<Pets> getPetById(@AuthenticationPrincipal User user, @PathVariable String petId) {
        return ResponseEntity.ok(userService.getUserInfo(user.getUsername()).getPets().get(0)) ;
    }

    @GetMapping
    @Operation(summary = "사용자의 모든 펫 조회", description = "사용자의 모든 반려동물의 정보를 조회합니다.")
    public ResponseEntity<List<PetResponseDto>> getPetsByUser(@AuthenticationPrincipal User user) {
        String userId = user.getUsername(); // 사용자 ID
        var pets = petService.getPetsByUser(userId);
        return ResponseEntity.ok(pets);
    }

    @PutMapping("/{petId}")
    @Operation(summary = "펫 정보 수정", description = "특정 반려동물의 정보를 수정합니다. 현재 사용불가능한 API")
    public ResponseEntity<String> updatePet(@AuthenticationPrincipal User user, @PathVariable String petId, @Valid @RequestBody PetRequestDto petRequestDto) {
        String userId = user.getUsername(); // 사용자 ID
//        petService.updatePet(userId, petId, petRequestDto);
        return ResponseEntity.ok("반려동물 정보가 성공적으로 업데이트되었습니다.");
    }

    @DeleteMapping("/{petId}")
    @Operation(summary = "펫 삭제", description = "특정 반려동물을 삭제합니다. 현재 사용불가능한 API")
    public ResponseEntity<Void> deletePet(@AuthenticationPrincipal User user, @PathVariable Integer petId) {
        String userId = user.getUsername(); // 사용자 ID
        petService.deletePet(userId, petId);
        return ResponseEntity.noContent().build();
    }
}
