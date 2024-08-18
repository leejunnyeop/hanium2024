package com.example.mypet.healthStatus.enums;

import lombok.Getter;

public enum SymptomColor {
    NONE(0, 0, "#464FE5", "건강 상태가 매우 양호해요. 현재 상태를 유지하면서 주기적인 운동과 균형 잡힌 식단을 제공해주세요. 예방적인 차원에서 정기 검진을 받는 것도 좋아요", "좋음" ),         // 0개
    MILD(1, 2, "#819AF8", "건강 상태가 대체로 좋아요. 약간의 주의가 필요할 수 있지만, 큰 문제는 없어보여요. 지속적인 모니터링과 함께 건강한 생활 습관을 유지해주세요.", "보통"),         // 1~2개
    MODERATE(3, 5, "#FB993C", "몇 가지 주의해야 할 점들이 있어요. 밥을 잘 먹지 않거나, 무기력함이 지속되면 추가 검진을 받아보는 것이 좋아요. 현재 상태를 꾸준히 모니터링하고, 건강 상태가 더 나빠지지 않도록 신경 써주세요.", "주의"),     // 3~5개
    SEVERE(6, 8, "#EA600C", "여러 건강 문제들이 나타나고 있어요. 특히 구토, 기침, 잇몸색 변화 등의 증상이 지속된다면 즉시 동물 병원에 방문해 정밀 검사를 받아보는 것이 필요해요. 평소보다 더욱 세심하게 돌봐주세요.", "경계"),       // 6~8개
    CRITICAL(9, 10, "#9A3912", "건강 상태가 매우 우려돼요. 대부분의 항목에서 문제가 발견되었으므로, 즉시 전문 수의사의 진료를 받아야 해요. 강아지의 건강을 신속하게 회복시키기 위해 필요한 모든 조치를 취해주세요.", "위험");    // 9~10개

    private final int minCount;
    private final int maxCount;
    @Getter
    private final String color;
    @Getter
    private final String diagnosis;
    @Getter
            private final String statusLevel;
    SymptomColor(int minCount, int maxCount, String color, String diagnosis, String statusLevel ) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.color = color;
        this.diagnosis = diagnosis;
        this.statusLevel = statusLevel;
    }

    public boolean isInRange(int count) {
        return count >= minCount && count <= maxCount;
    }

    public static SymptomColor fromCount(int count) {
        for (SymptomColor severity : SymptomColor.values()) {
            if (severity.isInRange(count)) {
                return severity;
            }
        }
        throw new IllegalArgumentException("Invalid symptom count: " + count);
    }
}
