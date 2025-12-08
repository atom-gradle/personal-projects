package com.atom.personnel.entity;

public enum Gender {

    MALE("男"),
    FEMALE("女"),
    OTHER("未知");

    private String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Gender safeValueOf(String value) {
        if (value == null) {
            return Gender.OTHER;
        }

        String upperValue = value.toUpperCase();
        for (Gender gender : Gender.values()) {
            if (upperValue.equals(gender.getDescription())) {
                return gender;
            }
        }
        return Gender.OTHER;
    }
}
