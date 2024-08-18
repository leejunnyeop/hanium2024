package com.example.mypet.healthStatus.controller;

import com.example.mypet.healthStatus.domain.dto.HealthStatusRequestDto;
import com.example.mypet.healthStatus.domain.dto.HealthStatusResponseDto;
import com.example.mypet.healthStatus.service.HealthStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/status")
@Log4j2
@Tag(name = "status", description = "사용자 펫 상태 API")
public class HealthStatusController {

    private final HealthStatusService healthStatusService;

    @PostMapping()
    @Operation(summary = "펫 상태 등록", description = "펫의 상태를 등록 및 업데이트합니다.")
    public ResponseEntity<HealthStatusResponseDto> createPet(@AuthenticationPrincipal User user, @Valid @RequestBody HealthStatusRequestDto healthStatusRequestDto) {
        log.info("createPet Call");
        log.info("healthStatusRequestDto: {}", healthStatusRequestDto);
        log.info("user: {}", user.getUsername());
        String userID = user.getUsername(); // id
        var savedPet = healthStatusService.statusSave(userID, healthStatusRequestDto);
        return ResponseEntity.ok(savedPet);
    }

    // 특정 날짜에 대한 상태를 조회
    @Operation(summary = "펫 상태 조회 ", description = "펫에 특정 날을 조회 합니다. 특정 날에 값이 없으면 null 반환")
    @GetMapping("/{date}")
    public ResponseEntity<HealthStatusResponseDto> getHealthStatus(@AuthenticationPrincipal User user, @Parameter(description = "건강 상태를 조회할 날자 형식", example = "2023-07-20") @PathVariable LocalDate date) {
        String userID = user.getUsername(); // id
        HealthStatusResponseDto healthStatus = healthStatusService.statusGet(userID, date);
        return ResponseEntity.ok(healthStatus);
    }


    // 주간 상태 목록을 조회(일요일부터 토요일까지)
    @Operation(summary = "펫 주간 상태 조회 ", description = "일요일부터 토요일까지 펫 주간 상태 조회를 합니다.")
    @GetMapping("/weekly/{date}")
    public ResponseEntity<List<HealthStatusResponseDto>> getWeeklyHealthStatuses(
            @AuthenticationPrincipal User user,
            @Parameter(description = "주간 건강 상태를 조회할 날자 형식", example = "2023-07-20") @PathVariable LocalDate date
    ) {
        String userID = user.getUsername(); // id
        List<HealthStatusResponseDto> statuses = healthStatusService.getWeeklyHealthStatuses(userID, date);
        return ResponseEntity.ok(statuses);
    }

    // 월간 상태 목록을 조회
    @Operation(summary = "펫 월간 상태 조회 ", description = "펫을 월간 상태를 조회를 합니다.")
    @GetMapping("/monthly/{date}")
    public ResponseEntity<List<HealthStatusResponseDto>> getMonthlyHealthStatuses(
            @AuthenticationPrincipal User user,
            @Parameter(description = "월간 건강 상태를 조회할 날자 형식", example = "2023-07-20") @PathVariable LocalDate date
    ) {
        String userID = user.getUsername(); // id
        List<HealthStatusResponseDto> statuses = healthStatusService.getMonthlyHealthStatuses(userID, date);
        return ResponseEntity.ok(statuses);
    }


}
