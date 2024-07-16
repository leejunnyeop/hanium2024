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

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;


    @Transactional
    @Override
    public HealthStatusDto statusSave(String userId, HealthStatusDto healthStatusDto) {
        try {
            HealthStatus statusById = statusRepository.findByUsersIdAndDate(userId, healthStatusDto.getDate())
                    .orElseThrow(() -> new ResourceNotFoundException("해당 날짜에는 이미 선택이 되었습니다."));
            HealthStatus save = statusRepository.save(statusById);
            return StatusMapper.toHealthStatusDto(save);
        } catch (Exception e) {
            throw new ServiceException("펫 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<HealthStatusDto> statusGet(String userId, String petId, LocalDate date) {
        try {
            Optional<HealthStatus> statusById = statusRepository.findByUsersIdAndPetIdAndDate(userId, petId, date);
            return statusById.map(StatusMapper::toHealthStatusDto);
        } catch (Exception e) {
            throw new ServiceException("펫 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
