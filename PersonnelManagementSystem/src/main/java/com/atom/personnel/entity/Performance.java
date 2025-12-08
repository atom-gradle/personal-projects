package com.atom.personnel.entity;

public enum Performance {

    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E");

    private String value;

    Performance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Performance safeValueOf(String value) {
        if (value == null) {
            return Performance.D;
        }

        String upperValue = value.toUpperCase();
        for (Performance performance : Performance.values()) {
            if (upperValue.equals(performance.getValue())) {
                return performance;
            }
        }
        return Performance.D;
    }

}
