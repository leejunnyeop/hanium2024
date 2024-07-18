package com.example.mypet.health.domain;

import com.example.mypet.health.domain.dto.HealthStatusDto;
import com.example.mypet.health.domain.entity.HealthStatus;
import com.example.mypet.health.domain.entity.Symptom;

public class StatusMapper {

    // DTO를 엔티티로 변환하는 메서드
    public static HealthStatus toHealthStatus(HealthStatusDto healthStatusDto, String userId) {

        return HealthStatus.builder()
                .petsId(healthStatusDto.getPetsId())
                .usersId(userId) // userId를 매개변수로 받아서 설정
                .date(healthStatusDto.getDate())
                .symptoms(healthStatusDto.getSymptoms())
                .comment(healthStatusDto.getComment())
                .status(healthStatusDto.getStatus())
                .color(healthStatusDto.getColor()) // 색상 설정
                .build();
    }

    // 엔티티를 DTO로 변환하는 메서드
    public static HealthStatusDto toHealthStatusDto(HealthStatus healthStatus) {
        return HealthStatusDto.builder()
                .petsId(healthStatus.getPetsId())
                .date(healthStatus.getDate())
                .symptoms(healthStatus.getSymptoms())
                .comment(healthStatus.getComment())
                .status(healthStatus.getStatus())
                .color(healthStatus.getColor())
                .build();
    }
}
