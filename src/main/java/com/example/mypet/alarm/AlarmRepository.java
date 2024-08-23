package com.example.mypet.alarm;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlarmRepository extends MongoRepository<Alarm, String> {
    List<Alarm> findByUser_IdOrderByCreatedDateDesc(String userId);
}
