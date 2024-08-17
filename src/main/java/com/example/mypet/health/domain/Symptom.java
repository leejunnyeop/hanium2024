package com.example.mypet.health.domain;

public enum Symptom {
    POOR_APPETITE("음식을 잘 못먹음"),
    ABNORMAL_BREATHING("숨소리/호흡이상"),
    LETHARGY("무기력함"),
    VOMITING("구토 증상"),
    REFUSING_AFFECTION("스킨십 거부"),
    FREQUENT_COUGH("잦은 기침"),
    WET_NOSE("코가 축축함"),
    ABNORMAL_STOOL("배변 색/상태 이상"),
    GUM_COLOR_CHANGE("잇몸색 변화"),
    WEIGHT_CHANGE("체중 변화");

    private final String description;

    Symptom(String description) {
        this.description = description;
    }

}
