package com.example.mypet.health.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "health_status")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthStatus {

    @Id
    private String id;

    private String usersId;
    private String petsId;

    @Schema(description = "기록 날짜", example = "2023-07-20")
    private LocalDate date;

    @Schema(description = "증상 리스트")
    private List<Symptom> symptoms; //증상리스트

    @Schema(description = "증상 코멘트", example = "활동량이 줄어들었어요")
    private String comment; // 증상 코멘트

    @Schema(description = "상태", example = "안좋음")
    private String status;

    @Schema(description = "색상", example = "#FF0000")
    private String color;


}
