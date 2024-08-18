package com.example.mypet.healthStatus.domain;

import com.example.mypet.healthStatus.domain.dto.HealthStatusRequestDto;
import com.example.mypet.healthStatus.domain.dto.HealthStatusResponseDto;
import com.example.mypet.healthStatus.domain.entity.HealthStatus;
import com.example.mypet.security.domain.users.Users;

public class HealthStatusMapper {

    // DTO를 엔티티로 변환하는 메서드
    public static HealthStatus toHealthStatus(Users users, HealthStatusRequestDto healthStatusRequestDto) {

        return HealthStatus.builder()
                .date(healthStatusRequestDto.getDate())
                .symptoms(healthStatusRequestDto.getSymptoms())
                .user(users)
                .build();
    }

    // 엔티티를 DTO로 변환하는 메서드
    public static HealthStatusRequestDto toHealthStatusDto(HealthStatus healthStatus) {
        return HealthStatusRequestDto.builder()
                .date(healthStatus.getDate())
                .symptoms(healthStatus.getSymptoms())
                .build();
    }

    public static HealthStatusResponseDto toHealthStatusResponseDto(HealthStatus healthStatus) {
        return HealthStatusResponseDto.builder()
                .date(healthStatus.getDate())
                .symptoms(healthStatus.getSymptoms())
                .build();
    }
}
