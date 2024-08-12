package com.example.mypet.find.repository;


import com.example.mypet.find.domain.entity.OwnerSearchBoard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OwnerSearchBoardRepository extends MongoRepository<OwnerSearchBoard, String> {
}
