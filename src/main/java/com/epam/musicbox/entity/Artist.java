package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.ObjectUtils;
import jakarta.inject.Singleton;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Artist {
    private Integer id;
    private String name;
    private String avatar;

    public Artist() {
    }

    public Artist(Integer id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return ObjectUtils.equals(id, artist.id)
                && ObjectUtils.equals(name, artist.name)
                && ObjectUtils.equals(avatar, artist.avatar);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id, name, avatar);
    }

    @Override
    public String toString() {
        return new StringBuilder("Artist{")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", avatar='").append(avatar).append('\'')
                .append('}')
                .toString();
    }

    @Singleton
    public static class Builder implements EntityBuilder<Artist> {

        private Builder() {
        }

        @Override
        public Artist build(ResultSet resultSet) throws HttpException {
            try {
                return new Artist(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("avatar"));
            } catch (SQLException e) {
                throw new HttpException(e);
            }
        }
    }
}
