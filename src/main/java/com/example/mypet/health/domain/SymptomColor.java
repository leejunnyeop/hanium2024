package com.example.mypet.health.domain;

public enum SymptomColor {
    NONE(0, 0, "#464FE5"),         // 0개
    MILD(1, 2, "#819AF8"),         // 1~2개
    MODERATE(3, 5, "#FB993C"),     // 3~5개
    SEVERE(6, 8, "#EA600C"),       // 6~8개
    CRITICAL(9, 10, "#9A3912");    // 9~10개

    private final int minCount;
    private final int maxCount;
    private final String color;

    SymptomColor(int minCount, int maxCount, String color) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.color = color;
    }

    public String getColor() {
        return color;
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
