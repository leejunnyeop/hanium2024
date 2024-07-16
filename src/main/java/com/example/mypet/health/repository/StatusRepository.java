package com.example.mypet.health.repository;

import com.example.mypet.health.domain.entity.HealthStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;

import java.util.Optional;

public interface StatusRepository extends MongoRepository<HealthStatus, String> {

 Optional<HealthStatus> findByUsersIdAndPetIdAndDate(String userId, String petId, LocalDate date);

 Optional<HealthStatus> findByUsersIdAndDate(String userId, LocalDate date);


}
