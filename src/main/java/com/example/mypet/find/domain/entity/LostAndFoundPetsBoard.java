package com.example.mypet.find.domain.entity;


import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.mypet.pet.domain.entity.Pets;
import com.example.mypet.security.domain.users.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostAndFoundPetsBoard {

    @Id
    private String id;

    private String stats;

    private String name;

    private String exterior;

    private String phoneNumber;

    private String imgUrl;


}
