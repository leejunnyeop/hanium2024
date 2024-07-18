package com.example.mypet.health.domain.entity;

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

    private LocalDate date;
    private List<Symptom> symptoms; //증상리스트
    private String comment; // 증상 코메트
    private String status;
    private String color; // 증상 개수에 따른 색상



}
