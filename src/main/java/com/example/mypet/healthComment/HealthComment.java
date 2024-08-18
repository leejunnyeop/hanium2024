package com.example.mypet.healthComment;

import com.example.mypet.security.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "health_comments")
@Getter
@Builder
public class HealthComment {
    @Id
    private String id;
    private LocalDate date;
    private String comment;

    @DBRef
    private Users user;
}
