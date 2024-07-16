package com.example.mypet.health.domain.dto;

import com.example.mypet.health.domain.entity.Symptom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthStatusDto {


    private String petId;
    private String usersId;

    private LocalDate date;
    private List<Symptom> symptoms; //증상리스트
    private String comment; // 증상 코메트
    private String status;
}
