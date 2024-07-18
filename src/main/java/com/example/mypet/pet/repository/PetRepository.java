package com.example.mypet.pet.repository;

import com.example.mypet.pet.domain.entity.Pets;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PetRepository extends MongoRepository<Pets, String> {
}
