package com.example.mypet.health.domain;

import com.example.mypet.health.domain.dto.HealthStatusDto;
import com.example.mypet.health.domain.dto.HealthStatusResponseDto;
import com.example.mypet.health.domain.entity.HealthStatus;
import com.example.mypet.security.domain.users.Users;

public class StatusMapper {

    // DTO를 엔티티로 변환하는 메서드
    public static HealthStatus toHealthStatus(Users users, HealthStatusDto healthStatusDto) {

        return HealthStatus.builder()
                .date(healthStatusDto.getDate())
                .symptoms(healthStatusDto.getSymptoms())
                .user(users)
                .build();
    }

    // 엔티티를 DTO로 변환하는 메서드
    public static HealthStatusDto toHealthStatusDto(HealthStatus healthStatus) {
        return HealthStatusDto.builder()
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
