package com.example.mypet.repository;

import com.example.mypet.domain.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PetRepository extends MongoRepository<Pet, String> {
}
