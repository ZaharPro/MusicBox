package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import jakarta.inject.Singleton;

import java.sql.ResultSet;
import java.sql.SQLException;

public enum Status {
    ACTIVE(0, "active"),
    BANNED(1, "banned");

    private final Integer id;
    private final String name;

    Status(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Status findById(int id) {
        for (Status status : Status.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }

    public static Status findByName(String name) {
        for (Status Status : Status.values()) {
            if (Status.getName().equals(name)) {
                return Status;
            }
        }
        return null;
    }

    @Singleton
    public static class Builder implements EntityBuilder<Status> {
        @Override
        public Status build(ResultSet resultSet) throws HttpException {
            try {
                int statusId = resultSet.getInt("status_id");
                return findById(statusId);
            } catch (SQLException e) {
                throw new HttpException(e);
            }
        }
    }
}
