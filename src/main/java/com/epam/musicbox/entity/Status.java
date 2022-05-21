package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Status {
    public static final String ACTIVE = "active";
    public static final String BANNED = "banned";

    private Integer id;
    private String name;

    public Status() {
    }

    public Status(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return ObjectUtils.equals(id, status.id) &&
                ObjectUtils.equals(name, status.name);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id, name);
    }

    @Override
    public String toString() {
        return new StringBuilder("Status{")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append('}')
                .toString();
    }

    public static class Builder implements EntityBuilder<Status> {
        @Override
        public Status build(ResultSet resultSet) throws HttpException {
            try {
                return new Status(resultSet.getInt("status_id"),
                        resultSet.getString("name"));
            } catch (SQLException e) {
                throw new HttpException(e.getMessage(), e);
            }
        }
    }
}
