package com.example.mypet.alarm;


import com.example.mypet.global.ex.UserNotFoundException;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final UsersRepository userRepository;
    private final AlarmRepository alarmRepository;

    @Transactional
    public Alarm createAlarm(String userId, AlarmRequestDto dto) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Alarm alarm = dto.toEntity(user);

        return alarmRepository.save(alarm);
    }

    @Transactional(readOnly = true)
    public List<AlarmResponseDto> getAlarm(String userId) {
        return alarmRepository.findByUser_IdOrderByCreatedDateDesc(userId).stream().map(
                AlarmResponseDto::fromEntity
        ).toList();
    }

    public void markAsRead(String alarmId) {
        Optional<Alarm> alarmOptional = alarmRepository.findById(alarmId);

        if (alarmOptional.isPresent()) {
            Alarm alarm = alarmOptional.get();
            alarm.setRead(true);  // 읽음 상태로 변경
            alarmRepository.save(alarm);  // 업데이트된 알람 저장
        } else {
            throw new RuntimeException("Alarm not found");
        }
    }
}
