package com.example.mypet.health.service;

import com.example.mypet.health.domain.dto.HealthStatusDto;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

public interface StatusService {

    public HealthStatusDto statusSave(String userId,HealthStatusDto healthStatusDto);

    public Optional<HealthStatusDto> statusGet(String userId, String petId, LocalDate data);

    public List<HealthStatusDto> getWeeklyHealthStatuses(String userId, String petId, LocalDate date);

    public List<HealthStatusDto> getMonthlyHealthStatuses(String userId, String petId, LocalDate date);


}
