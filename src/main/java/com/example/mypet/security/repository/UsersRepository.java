package com.example.mypet.security.repository;

import com.example.mypet.security.domain.users.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<Users, String> {
    Optional<Users> findByName(String username);
    Optional<Users> findByEmail(String email);



}
