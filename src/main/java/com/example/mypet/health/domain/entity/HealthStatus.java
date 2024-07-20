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

    @Schema(description = "등록날짜", example = "2024-05-06")
    private LocalDate date;

    @Schema(description = "증상리스트", example =
            "\"symptoms\" : \n" +
            "     [\n" +
            "        {\n" +
            "            \"name\": \"똥색 안좋음\",\n" +
            "            \"checked\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"기침 많이 함\",\n" +
            "            \"checked\": false\n" +
            "        }\n" +
            "    ]")
    private List<Symptom> symptoms; //증상리스트

    @Schema(description = "증상 코멘트" , example = "기침은 없지만 똥색이 별로예요.")
    private String comment; // 증상 코메트

    @Schema(description = "상태", example = "심각")
    private String status;

    private String color; // 증상 개수에 따른 색상



}
