package com.atom.personnel.entity;

public enum Position {

    STAFF("普通职工"),
    DIRECTOR("负责人"),
    MANAGER("经理"),
    SENIOR_MANAGER("高级经理"),
    INTERN("实习生");

    private String description;

    Position(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public static Position safeValueOf(String value) {
        if (value == null) {
            return Position.STAFF;
        }

        String upperValue = value.toUpperCase();
        for (Position position : Position.values()) {
            if (upperValue.equals(position.getDescription())) {
                return position;
            }
        }
        return Position.STAFF;
    }

}
