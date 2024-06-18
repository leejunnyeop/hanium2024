package com.example.mypet.security.repository;

import com.example.mypet.security.domain.refresh.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    RefreshToken findByEmail(String email);
    void deleteByEmail(String email);
}
