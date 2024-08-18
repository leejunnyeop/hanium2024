package com.example.mypet.healthStatus.domain;

import com.example.mypet.healthStatus.domain.dto.HealthStatusDto;
import com.example.mypet.healthStatus.domain.dto.HealthStatusResponseDto;
import com.example.mypet.healthStatus.domain.entity.HealthStatus;
import com.example.mypet.security.domain.users.Users;

public class HealthStatusMapper {

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
