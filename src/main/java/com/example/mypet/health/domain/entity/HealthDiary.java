package com.example.mypet.health.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("HealthDiary")
@AllArgsConstructor
@NoArgsConstructor
public class HealthDiary {

    @Id
    private String id;

    private LocalDate diray;

}
