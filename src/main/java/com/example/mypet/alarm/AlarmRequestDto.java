package com.example.mypet.alarm;

import com.example.mypet.security.domain.users.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlarmRequestDto {
    private String location;
    private AlarmedType alarmType;
    private String boardId;


    public Alarm toEntity(Users user) {
        return Alarm.builder()
                .location(this.location)
                .alarmType(this.alarmType)
                .boardId(this.boardId)
                .user(user)
                .build();
    }
}