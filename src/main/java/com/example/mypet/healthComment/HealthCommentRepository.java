package com.example.mypet.healthComment;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface HealthCommentRepository extends MongoRepository<HealthComment, String>{
    Optional<HealthComment> findFirstByUser_IdAndDate(String userId, LocalDate date);
}
