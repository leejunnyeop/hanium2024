package com.example.mypet.petSearchBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PetSearchBoardRepository extends MongoRepository<PetSearchBoard, String>{
    List<PetSearchBoard> findByUser_Id(String userId);
    Page<PetSearchBoard> findByUser_IdNot(String userId, Pageable pageable);
}
