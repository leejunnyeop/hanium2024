package com.example.mypet.health.domain;

import com.example.mypet.health.domain.dto.HealthStatusDto;
import com.example.mypet.health.domain.entity.HealthStatus;

public class StatusMapper {

    public static HealthStatus toHealthStatus(HealthStatusDto healthStatusDto){
        return HealthStatus.builder()
                .petId(healthStatusDto.getPetId())
                .usersId(healthStatusDto.getUsersId())
                .status(healthStatusDto.getStatus())
                .comment(healthStatusDto.getComment())
                .date(healthStatusDto.getDate())
                .symptoms(healthStatusDto.getSymptoms())
                .build();

    }

    public static HealthStatusDto toHealthStatusDto(HealthStatus healthStatus){
        return HealthStatusDto.builder()
                .petId(healthStatus.getPetId())
                .usersId(healthStatus.getUsersId())
                .status(healthStatus.getStatus())
                .comment(healthStatus.getComment())
                .date(healthStatus.getDate())
                .symptoms(healthStatus.getSymptoms())
                .build();

    }
}
