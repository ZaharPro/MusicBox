package com.epam.musicbox.entity;

public enum Role implements Entity<Integer> {
    GUEST("guest", -1),
    USER("user", 1),
    ADMIN("admin", 2);

    private final String value;
    private final int id;

    Role(String value, int id) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Integer getId() {
        return id;
    }

    public static Role findById(int id) {
        for (Role role : Role.values()) {
            if (role.id == id) {
                return role;
            }
        }
        return null;
    }

    public static Role findByValue(String value) {
        for (Role role : Role.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        return null;
    }
}