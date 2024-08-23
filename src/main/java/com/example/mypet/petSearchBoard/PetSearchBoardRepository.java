package com.example.mypet.petSearchBoard;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PetSearchBoardRepository extends MongoRepository<PetSearchBoard, String>{
    List<PetSearchBoard> findByUser_Id(String userId);

    @Query(value = "{ 'user.$id': { $ne: ?0 } }")
    Page<PetSearchBoard> findByUser_IdNot(ObjectId userId, Pageable pageable);
}
