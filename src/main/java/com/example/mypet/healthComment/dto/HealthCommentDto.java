package com.example.mypet.healthComment.dto;

import com.example.mypet.healthComment.HealthComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
public class HealthCommentDto {
    @Schema(description = "기록 날짜", example = "2025-08-20")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Schema(description = "커멘트", example = "불안해하는 모습이 보여요")
    private String comment;


}
