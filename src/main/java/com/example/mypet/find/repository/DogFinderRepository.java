package com.example.mypet.find.repository;

import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DogFinderRepository extends MongoRepository<LostAndFoundPetsBoard, String> {
}
