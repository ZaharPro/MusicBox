package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import jakarta.inject.Singleton;

import java.sql.ResultSet;
import java.sql.SQLException;

public enum Role {
    USER(0, "user"),
    ADMIN(1, "admin");

    private final int id;
    private final String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role findById(int id) {
        for (Role role : Role.values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        return null;
    }

    public static Role findByName(String name) {
        for (Role role : Role.values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return null;
    }

    @Singleton
    public static class Builder implements EntityBuilder<Role> {
        @Override
        public Role build(ResultSet resultSet) throws HttpException {
            try {
                int roleId = resultSet.getInt("role_id");
                return findById(roleId);
            } catch (SQLException e) {
                throw new HttpException(e);
            }
        }
    }
}