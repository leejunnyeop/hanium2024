package com.example.mypet.healthStatus.service;

import com.example.mypet.healthStatus.domain.dto.HealthStatusDto;
import com.example.mypet.healthStatus.domain.dto.HealthStatusResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface HealthStatusService {

    HealthStatusResponseDto statusSave(String userId, HealthStatusDto healthStatusDto);

    HealthStatusResponseDto statusGet(String userId, LocalDate date);


    List<HealthStatusResponseDto> getWeeklyHealthStatuses(String userId, LocalDate date);

    List<HealthStatusResponseDto> getMonthlyHealthStatuses(String userId, LocalDate date);


}
