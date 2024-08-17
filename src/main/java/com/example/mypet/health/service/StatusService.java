package com.example.mypet.health.service;

import com.example.mypet.health.domain.dto.HealthStatusDto;
import com.example.mypet.health.domain.dto.HealthStatusResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface StatusService {

    HealthStatusResponseDto statusSave(String userId, HealthStatusDto healthStatusDto);

    HealthStatusResponseDto statusGet(String userId, LocalDate date);


    List<HealthStatusResponseDto> getWeeklyHealthStatuses(String userId, LocalDate date);

    List<HealthStatusResponseDto> getMonthlyHealthStatuses(String userId, LocalDate date);


}
