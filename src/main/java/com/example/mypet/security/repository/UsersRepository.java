package com.example.mypet.security.repository;

import com.example.mypet.security.domain.users.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users, String> {

    Users findByEmail(String email);
}
