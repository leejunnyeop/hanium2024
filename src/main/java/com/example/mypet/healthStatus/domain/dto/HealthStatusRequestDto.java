package com.example.mypet.healthStatus.domain.dto;

import com.example.mypet.healthStatus.enums.Symptom;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class HealthStatusRequestDto {

    @NotNull
    @Schema(description = "기록 날짜", example = "2023-07-20")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Schema(description = "증상 리스트")
    private Set<Symptom> symptoms; // 증상리스트
}
