package com.example.mypet.petSearchBoard;

import com.example.mypet.pet.domain.Gender;
import com.example.mypet.security.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Document(collection = "pet_search_boards")
public class PetSearchBoard {
    @Id
    private String id;
    private String title;
    private List<String> imageUrlList;
    private String name;
    private String contact;
    private String specificLocation;
    private Double latitude;
    private Double longitude;
    private LocalDateTime lostDateTime;
    private String situation;
    private Gender petGender;
    private Boolean isNeutered;
    private String petBreed;
    private LocalDate birthDate;
    private String petDescription;
    private String content;

    @DBRef
    private Users user;
}
