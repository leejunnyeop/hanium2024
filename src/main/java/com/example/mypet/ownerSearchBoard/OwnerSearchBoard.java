package com.example.mypet.ownerSearchBoard;

import com.example.mypet.pet.domain.Gender;
import com.example.mypet.security.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Document(collection = "owner_search_boards")
public class OwnerSearchBoard {
    @Id
    private String id;

    private String title;
    private List<String> imageUrlList;
    private String name;
    private String reporterContact;
    private String specificLocation;
    private Double latitude;
    private Double longitude;
    private LocalDateTime foundDateTime;
    private String situation;
    private Gender petGender;
    private String petBreed;
    private String petDescription;
    private String content;

    @DBRef
    private Users user;
}
