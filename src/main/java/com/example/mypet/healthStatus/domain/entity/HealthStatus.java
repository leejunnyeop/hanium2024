package com.example.mypet.healthStatus.domain.entity;

import com.example.mypet.healthStatus.enums.Symptom;
import com.example.mypet.security.domain.users.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Document(collection = "health_status")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthStatus {

    @Id
    private String id;

    @Schema(description = "기록 날짜", example = "2023-07-20")
    private LocalDate date;

    @Schema(description = "증상 리스트")
    private Set<Symptom> symptoms; //증상리스트

    @DBRef
    private Users user;

    public void updateSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

}
