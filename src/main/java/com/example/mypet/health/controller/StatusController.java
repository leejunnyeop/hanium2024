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
import java.util.List;
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

    // 특정 날짜에 대한 상태를 조회하는 엔드포인트
    @Operation(summary = "펫 상태 조회 ", description = "펫에 특정 날을 조회 합니다.")
    @GetMapping("/{petId}/{date}")
    public ResponseEntity<Optional<HealthStatusDto>> getHealthStatus(@AuthenticationPrincipal User user, @PathVariable String petId, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        String userID = user.getUsername(); // id
        Optional<HealthStatusDto> status = statusService.statusGet(userID, petId, localDate);
        return ResponseEntity.ok(status);
    }

    // 주간 상태 목록을 조회하는 엔드포인트 (일요일부터 토요일까지)
    @Operation(summary = "펫 주간 상태 조회 ", description = "펫 주간 상태 조회를 합니다.")
    @GetMapping("/{petId}/weekly")
    public ResponseEntity<List<HealthStatusDto>> getWeeklyHealthStatuses(@AuthenticationPrincipal User user, @PathVariable String petId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        String userID = user.getUsername(); // id
        List<HealthStatusDto> statuses = statusService.getWeeklyHealthStatuses(userID, petId, localDate);
        return ResponseEntity.ok(statuses);
    }

    // 월간 상태 목록을 조회하는 엔드포인트
    @Operation(summary = "펫 월간 상태 조회 ", description = "펫을 월간 상태를 조회를 합니다.")
    @GetMapping("/{petId}/monthly")
    public ResponseEntity<List<HealthStatusDto>> getMonthlyHealthStatuses(@AuthenticationPrincipal User user, @PathVariable String petId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        String userID = user.getUsername(); // id
        List<HealthStatusDto> statuses = statusService.getMonthlyHealthStatuses(userID, petId, localDate);
        return ResponseEntity.ok(statuses);
    }
}
