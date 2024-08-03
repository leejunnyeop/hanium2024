package com.example.mypet.health.service;

import com.example.mypet.global.ex.BaseException;
import com.example.mypet.global.ex.ServiceException;
import com.example.mypet.health.domain.StatusMapper;
import com.example.mypet.health.domain.dto.HealthStatusDto;
import com.example.mypet.health.domain.entity.HealthStatus;
import com.example.mypet.health.repository.StatusRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Transactional
    @Override
    public HealthStatusDto statusSave(String userId, @Valid HealthStatusDto healthStatusDto) {
        try {
            Optional<HealthStatus> statusById = statusRepository.findByUsersIdAndDate(userId, healthStatusDto.getDate());

            if (statusById.isPresent()) {
                throw new IllegalArgumentException("해당 날짜에는 이미 선택이 되었습니다.");
            }


            HealthStatus healthStatus = StatusMapper.toHealthStatus(healthStatusDto, userId);

            HealthStatus savedStatus = statusRepository.save(healthStatus);
            return StatusMapper.toHealthStatusDto(savedStatus);
        } catch (IllegalArgumentException e) {
            // 변경된 부분: IllegalArgumentException을 BaseException으로 변경
            throw new BaseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {

            throw new ServiceException("상태 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

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

    @Transactional(readOnly = true)
    @Override
    public List<HealthStatusDto> getWeeklyHealthStatuses(String userId, String petId, LocalDate date) {
        try {
            LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));
            LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            List<HealthStatus> statuses = statusRepository.findByUsersIdAndPetsIdAndDateBetween(userId, petId, startOfWeek, endOfWeek);
            return statuses.stream()
                    .map(StatusMapper::toHealthStatusDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {

            throw new ServiceException("주간 상태 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<HealthStatusDto> getMonthlyHealthStatuses(String userId, String petId, LocalDate date) {
        try {
            LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
            List<HealthStatus> statuses = statusRepository.findByUsersIdAndPetsIdAndDateBetween(userId, petId, startOfMonth, endOfMonth);
            return statuses.stream()
                    .map(StatusMapper::toHealthStatusDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {

            throw new ServiceException("월간 상태 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
