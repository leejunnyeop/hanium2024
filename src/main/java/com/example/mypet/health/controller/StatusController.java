package com.example.mypet.health.controller;

import com.example.mypet.health.domain.dto.HealthStatusDto;

import com.example.mypet.health.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/status")
@Tag(name = "Status Controller", description = "사용자 펫 상태 API")
public class StatusController {

    private final StatusService statusService;

    @PostMapping()
    @Operation(summary = "펫 상태 등록", description = "펫의 상태를 등록합니다.")
    @Parameter(description = "펫 상태 등록 날짜 형식", example = "2023-07-20")
    public ResponseEntity<HealthStatusDto> createPet(@AuthenticationPrincipal User user, @Valid @RequestBody HealthStatusDto healthStatusDto) {
        String userID = user.getUsername(); // id
        HealthStatusDto savedPet = statusService.statusSave(userID, healthStatusDto);
        return ResponseEntity.ok(savedPet);
    }

    @GetMapping("/{petId}/{date}")
    @Operation(summary = "펫 상태 조회", description = "펫의 특정 날짜 상태를 조회합니다.")
    @Parameter(description = "건강 상태를 조회할 날짜 형식", example = "20230720")
    public ResponseEntity<Optional<HealthStatusDto>> getHealthStatus(@AuthenticationPrincipal User user, @PathVariable(name = "petId") String petId, @PathVariable(name = "date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        String userID = user.getUsername(); // id
        Optional<HealthStatusDto> status = statusService.statusGet(userID, petId, localDate);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{petId}/weekly")
    @Operation(summary = "펫 주간 상태 조회", description = "펫의 주간 상태를 조회합니다.")
    @Parameter(description = "주간 건강 상태를 조회할 날짜", example = "20230720")
    public ResponseEntity<List<HealthStatusDto>> getWeeklyHealthStatuses(@AuthenticationPrincipal User user, @PathVariable(name = "petId") String petId, @RequestParam(name = "date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        String userID = user.getUsername(); // id
        List<HealthStatusDto> statuses = statusService.getWeeklyHealthStatuses(userID, petId, localDate);
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/{petId}/monthly")
    @Operation(summary = "펫 월간 상태 조회", description = "펫의 월간 상태를 조회합니다.")
    @Parameter(description = "월간 건강 상태를 조회할 날짜 형식", example = "20230720")
    public ResponseEntity<List<HealthStatusDto>> getMonthlyHealthStatuses(@AuthenticationPrincipal User user, @PathVariable(name = "petId") String petId, @RequestParam(name = "date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        String userID = user.getUsername(); // id
        List<HealthStatusDto> statuses = statusService.getMonthlyHealthStatuses(userID, petId, localDate);
        return ResponseEntity.ok(statuses);
    }
}
