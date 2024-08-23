package com.example.mypet.alarm;

import lombok.Getter;

@Getter
public enum AlarmedType {
    LOST_DOG_FOUND("반려견 발견 알림", "비문 조회를 통해 실종된 반려견과 일치하는 강아지를 발견했다는 연락이 왔어요."),
    STRAY_DOG_NEARBY("근처 유기견 알림", "근처에 도움이 필요한 강아지가 있어요. 한 생명을 인근 보호소로 안전하게 인도해보세요.");

    // Getter 메소드
    private final String title;
    private final String description;

    // Enum 생성자
    AlarmedType(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
