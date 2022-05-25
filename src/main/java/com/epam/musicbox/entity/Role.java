package com.epam.musicbox.entity;

import com.epam.musicbox.util.ObjectUtils;
import com.epam.musicbox.exception.HttpException;
import jakarta.inject.Singleton;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Role {
    private Integer id;
    private String name;

    public Role() {
    }

    public Role(Integer id, String name) {
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
        Role role = (Role) o;
        return ObjectUtils.equals(id, role.id) &&
                ObjectUtils.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id, name);
    }

    @Override
    public String toString() {
        return new StringBuilder("Role{")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append('}')
                .toString();
    }

    @Singleton
    public static class Builder implements EntityBuilder<Role> {

        private Builder() {
        }

        @Override
        public Role build(ResultSet resultSet) throws HttpException {
            try {
                return new Role(resultSet.getInt("role_id"),
                        resultSet.getString("name"));
            } catch (SQLException e) {
                throw new HttpException(e);
            }
        }
    }
}