package com.example.mypet.pet.repository;

import com.example.mypet.pet.domain.entity.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PetRepository extends MongoRepository<Pet, String> {
}
