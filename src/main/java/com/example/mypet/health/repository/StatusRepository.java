package com.example.mypet.health.repository;

import com.example.mypet.health.domain.entity.HealthStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StatusRepository extends MongoRepository<HealthStatus, String> {

    Optional<HealthStatus> findByUser_IdAndDate(String userId, LocalDate date);
    List<HealthStatus> findByUser_IdAndDateBetweenOrderByDate(String userId, LocalDate startDate, LocalDate endDate);
}
