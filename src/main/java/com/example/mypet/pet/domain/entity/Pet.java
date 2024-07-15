package com.example.pet.domain.entity;

import com.example.pet.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    private String id;
    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private String breed;
    private String description;
    private boolean neutered;  // 중성화 여부 추가
    private String imageUrl;   // 이미지 URL 추가

}
