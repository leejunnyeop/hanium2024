package com.example.mypet.alarm;

import com.example.mypet.security.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Document(collection = "alarms")
public class Alarm {
    @Id
    private String id;

    private String location;
    private AlarmedType alarmType;
    private String boardId;

    @Builder.Default
    private boolean isRead = false;  // 알람이 읽혔는지 여부를 저장하는 필드
    @CreatedDate
    private LocalDateTime createdDate;

    @DBRef
    private Users user;
}
