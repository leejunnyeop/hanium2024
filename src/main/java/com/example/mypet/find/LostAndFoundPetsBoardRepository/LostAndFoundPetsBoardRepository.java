package com.example.mypet.find.LostAndFoundPetsBoardRepository;

import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;
import com.example.mypet.find.domain.entity.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LostAndFoundPetsBoardRepository extends MongoRepository<LostAndFoundPetsBoard, String> {

    Page<LostAndFoundPetsBoard> findAllByPostType(PostType postType, Pageable pageable);
}
