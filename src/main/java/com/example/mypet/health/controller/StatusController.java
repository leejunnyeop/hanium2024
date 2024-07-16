package com.example.mypet.health.controller;


import com.example.mypet.health.domain.dto.HealthStatusDto;
import com.example.mypet.health.service.StatusService;
import com.example.mypet.security.domain.users.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/status")
@Tag(name = "Stats Controller", description = "사용자 펫 상태 API" )
public class StatusController {

    private final StatusService statusService;

    @PostMapping
    @Operation(summary = "펫 상태 등록 ", description = "펫을 상태를 등록합니다.")
    public ResponseEntity<HealthStatusDto> createPet(@AuthenticationPrincipal User user, @RequestBody HealthStatusDto healthStatusDto) {
        String userID = user.getUsername(); // id
        HealthStatusDto savedPet = statusService.statusSave(userID, healthStatusDto);
        return ResponseEntity.ok(savedPet);
    }

    @GetMapping("/{petId}/{date}")
    public ResponseEntity<Optional<HealthStatusDto>> getHealthStatus(@AuthenticationPrincipal Users user, @PathVariable String petId, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        Optional<HealthStatusDto> status = statusService.statusGet(user.getId(), petId, localDate);
        return ResponseEntity.ok(status);
    }
}
