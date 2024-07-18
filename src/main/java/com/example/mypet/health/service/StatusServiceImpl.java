package com.example.mypet.health.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.directory.model.ServiceException;
import com.example.mypet.health.domain.StatusMapper;
import com.example.mypet.health.domain.dto.HealthStatusDto;
import com.example.mypet.health.domain.entity.HealthStatus;

import com.example.mypet.health.repository.StatusRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import java.util.Optional;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;


    @Transactional
    @Override
    public HealthStatusDto statusSave(String userId, HealthStatusDto healthStatusDto) {
        try {

            Optional<HealthStatus> statusById = statusRepository.findByUsersIdAndDate(userId, healthStatusDto.getDate());

            if (statusById.isPresent()) {
                throw new IllegalArgumentException("해당 날짜에는 이미 선택이 되었습니다.");
            }


            // 새로운 상태 기록 엔티티 변환
            HealthStatus healthStatus = StatusMapper.toHealthStatus(healthStatusDto, userId);


            HealthStatus savedStatus = statusRepository.save(healthStatus);
            return StatusMapper.toHealthStatusDto(savedStatus);
        } catch (ResourceNotFoundException e) {
            throw e; // 이미 처리된 예외는 재던집니다.
        } catch (Exception e) {
            throw new ServiceException("펫 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    // 해당 날짜에 조회는 메소드
    @Transactional(readOnly = true)
    @Override
    public Optional<HealthStatusDto> statusGet(String userId, String petId, LocalDate date) {
        try {

            Optional<HealthStatus> statusById = statusRepository.findByUsersIdAndPetsIdAndDate(userId, petId, date);
            return statusById.map(StatusMapper::toHealthStatusDto);
        } catch (Exception e) {
            throw new ServiceException("펫 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 주간 상태 목록 조회 메서드 (일요일부터 토요일까지)
    @Transactional(readOnly = true)
    public List<HealthStatusDto> getWeeklyHealthStatuses(String userId, String petId, LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<HealthStatus> statuses = statusRepository.findByUsersIdAndPetsIdAndDateBetween(userId, petId, startOfWeek, endOfWeek);
        return statuses.stream()
                .map(StatusMapper::toHealthStatusDto)
                .collect(Collectors.toList());
    }

    // 월간 상태 목록 조회 메서드
    @Transactional(readOnly = true)
    public List<HealthStatusDto> getMonthlyHealthStatuses(String userId, String petId, LocalDate date) {
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        List<HealthStatus> statuses = statusRepository.findByUsersIdAndPetsIdAndDateBetween(userId, petId, startOfMonth, endOfMonth);
        return statuses.stream()
                .map(StatusMapper::toHealthStatusDto)
                .collect(Collectors.toList());
    }
}
