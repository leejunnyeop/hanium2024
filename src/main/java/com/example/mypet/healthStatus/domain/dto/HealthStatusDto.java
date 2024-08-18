package com.example.mypet.healthStatus.domain.dto;

import com.example.mypet.healthStatus.enums.Symptom;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class HealthStatusDto {

    @NotNull
    @Schema(description = "기록 날짜", example = "2023-07-20")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Schema(description = "증상 리스트")
    private Set<Symptom> symptoms; // 증상리스트
}
