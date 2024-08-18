package com.example.mypet.healthComment;


import com.example.mypet.healthComment.dto.HealthCommentDto;
import com.example.mypet.security.domain.users.Users;

public class HealthCommentMapper {
    public static HealthCommentDto toHealthCommentDto(HealthComment healthComment) {
        if (healthComment == null) {
            return null; // exception 처리 생각
        }

        return HealthCommentDto.builder()
                .date(healthComment.getDate())
                .comment(healthComment.getComment())
                .build();
    }
    public static HealthComment toHealthComment(HealthCommentDto healthCommentDto, Users user) {
        if (healthCommentDto == null) {
            return null; // exception 처리 생각
        }

        return HealthComment.builder()
                .date(healthCommentDto.getDate())
                .comment(healthCommentDto.getComment())
                .user(user)
                .build();
    }
}
