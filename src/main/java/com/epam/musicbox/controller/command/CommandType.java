package com.epam.musicbox.controller.command;

public enum CommandType {
    LOGIN("login"),
    LOGOUT("logout"),
    SING_UP("singup");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CommandType of(String name) {
        for (CommandType type : CommandType.values()) {
            if (name.equals(type.getName())) {
                return type;
            }
        }
        return null;
    }
}
