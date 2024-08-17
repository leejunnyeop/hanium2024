package com.example.mypet.health.service;

import com.example.mypet.global.ex.ServiceException;
import com.example.mypet.global.ex.UserNotFoundException;
import com.example.mypet.health.domain.StatusMapper;
import com.example.mypet.health.domain.dto.HealthStatusDto;
import com.example.mypet.health.domain.dto.HealthStatusResponseDto;
import com.example.mypet.health.domain.entity.HealthStatus;
import com.example.mypet.health.repository.StatusRepository;
import com.example.mypet.security.repository.UsersRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;
    private final UsersRepository usersRepository;
    @Transactional
    @Override
    public HealthStatusResponseDto statusSave(String userId, @Valid HealthStatusDto healthStatusDto) {
        var user = usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
        HealthStatus healthStatus = statusRepository.findByUser_IdAndDate(userId, healthStatusDto.getDate()).orElse(null);

        // 새로 등록
        if (healthStatus == null){
            var status = StatusMapper.toHealthStatus(user,  healthStatusDto);
            var savedStatus = statusRepository.save(status);
            return StatusMapper.toHealthStatusResponseDto(savedStatus);
        }
        // update
        healthStatus.updateSymptoms(healthStatusDto.getSymptoms());
        var savedStatus = statusRepository.save(healthStatus);
        return StatusMapper.toHealthStatusResponseDto(savedStatus);
    }

    @Transactional(readOnly = true)
    @Override
    public HealthStatusResponseDto statusGet(String userId, LocalDate date) {

        var status = statusRepository.findByUser_IdAndDate(userId, date).orElse(null);
        if (status == null) return null;
        return StatusMapper.toHealthStatusResponseDto(status);

    }


    @Transactional(readOnly = true)
    @Override
    public List<HealthStatusResponseDto> getWeeklyHealthStatuses(String userId, LocalDate date) {
        try {
            LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));
            LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            List<HealthStatus> statuses = statusRepository.findByUser_IdAndDateBetweenOrderByDate(userId, startOfWeek, endOfWeek);
            return statuses.stream()
                    .map(StatusMapper::toHealthStatusResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {

            throw new ServiceException("주간 상태 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<HealthStatusResponseDto> getMonthlyHealthStatuses(String userId, LocalDate date) {
        try {
            LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
            List<HealthStatus> statuses = statusRepository.findByUser_IdAndDateBetweenOrderByDate(userId, startOfMonth, endOfMonth);
            return statuses.stream()
                    .map(StatusMapper::toHealthStatusResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {

            throw new ServiceException("월간 상태 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


}
