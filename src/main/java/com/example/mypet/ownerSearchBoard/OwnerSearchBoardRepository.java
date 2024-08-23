package com.example.mypet.ownerSearchBoard;


import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OwnerSearchBoardRepository extends MongoRepository<OwnerSearchBoard, String> {
    List<OwnerSearchBoard> findByUser_Id(String userId);

    @Query(value = "{ 'user.$id': { $ne: ?0 } }")
    Page<OwnerSearchBoard> findByUserIdNot(ObjectId userId, Pageable pageable);
}
