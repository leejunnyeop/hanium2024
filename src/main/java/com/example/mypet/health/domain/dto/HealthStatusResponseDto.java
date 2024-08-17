package com.example.mypet.health.domain.dto;

import com.example.mypet.health.domain.Symptom;
import com.example.mypet.health.domain.SymptomColor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@NoArgsConstructor()
@Builder
@AllArgsConstructor
public class HealthStatusResponseDto {
    @NotNull
    @Schema(description = "기록 날짜", example = "2023-07-20")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Schema(description = "증상 리스트")
    private Set<Symptom> symptoms; // 증상리스트

    public Integer getSymptomCount() {
        return symptoms.size();
    }

    public String getSymptomColor(){
        var color = SymptomColor.fromCount(symptoms.size());
        return color.getColor();
    }
}
