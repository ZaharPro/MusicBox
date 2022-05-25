package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.ObjectUtils;
import jakarta.inject.Singleton;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Playlist {
    private Integer id;
    private String name;
    private Integer userId;

    public Playlist() {
    }

    public Playlist(Integer id, String name, Integer userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return ObjectUtils.equals(id, playlist.id) &&
                ObjectUtils.equals(name, playlist.name) &&
                ObjectUtils.equals(userId, playlist.userId);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id, name, userId);
    }

    @Override
    public String toString() {
        return new StringBuilder("Playlist{")
                .append("id=").append(id)
                .append("name= '").append(name).append('\'')
                .append("userId=").append(userId)
                .append('}')
                .toString();
    }

    @Singleton
    public static class Builder implements EntityBuilder<Playlist> {

        private Builder() {
        }

        @Override
        public Playlist build(ResultSet resultSet) throws HttpException {
            try {
                return new Playlist(resultSet.getInt("playlist_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("user_id"));
            } catch (SQLException e) {
                throw new HttpException(e);
            }
        }
    }
}
