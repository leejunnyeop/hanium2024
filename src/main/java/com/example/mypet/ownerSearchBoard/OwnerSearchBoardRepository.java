package com.example.mypet.ownerSearchBoard;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OwnerSearchBoardRepository extends MongoRepository<OwnerSearchBoard, String> {
    List<OwnerSearchBoard> findByUser_Id(String userId);
    Page<OwnerSearchBoard> findByUser_IdNot(String userId, Pageable pageable);
}