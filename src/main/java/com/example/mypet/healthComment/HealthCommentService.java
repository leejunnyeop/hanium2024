package com.example.mypet.healthComment;

import com.example.mypet.global.ex.UserNotFoundException;
import com.example.mypet.healthComment.dto.HealthCommentDto;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HealthCommentService {
    private final HealthCommentRepository healthCommentRepository;
    private final UsersRepository usersRepository;
    @Transactional
    public HealthCommentDto saveHealthComment(String userId, HealthCommentDto healthCommentDto) {

        var user = usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));

        var comment = HealthCommentMapper.toHealthComment(healthCommentDto, user);
        var savedComment = healthCommentRepository.save(comment);
        return HealthCommentMapper.toHealthCommentDto(savedComment);
    }

    @Transactional(readOnly = true)
    public HealthCommentDto getHealthComment(String userId, LocalDate date) {
        var comment = healthCommentRepository.findFirstByUser_IdAndDate(userId, date).orElse(null);

        if (comment == null) return null;
        return HealthCommentMapper.toHealthCommentDto(comment);
    }
}
