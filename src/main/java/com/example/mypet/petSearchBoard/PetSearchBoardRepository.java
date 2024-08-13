package com.example.mypet.petSearchBoard;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PetSearchBoardRepository extends MongoRepository<PetSearchBoard, String>{
}
