package com.example.mypet.alarm;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AlarmResponseDto {
    private String id;
    private String location;
    private AlarmedType alarmType;
    private String boardId;
    private boolean isRead;
    private String userId;
    private String userName;
    private LocalDateTime createdAt;

    public String getAlarmTitle(){
        return this.alarmType.getTitle();
    }
    public String getAlarmDescription(){
        return this.alarmType.getDescription();
    }

    public static AlarmResponseDto fromEntity(Alarm alarm) {
        return AlarmResponseDto.builder()
                .id(alarm.getId())
                .location(alarm.getLocation())
                .alarmType(alarm.getAlarmType())
                .boardId(alarm.getBoardId())
                .isRead(alarm.isRead())
                .userId(alarm.getUser().getId())
                .userName(alarm.getUser().getName())
                .createdAt(alarm.getCreatedDate())
                .build();
    }
}