package com.example.mypet.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "pets")
@Getter
@ToString
@Setter
public class Pet {
    @Id
    private String id;

    private String name;
    @CreatedDate
    private LocalDateTime createdAt;
}
