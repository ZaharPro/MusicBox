package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Playlist {
    private Long id;
    private String name;
    private Long userId;

    public Playlist() {
    }

    public Playlist(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public static class Builder implements EntityBuilder<Playlist> {
        private static final Builder instance = new Builder();

        private Builder() {
        }

        public static Builder getInstance() {
            return instance;
        }

        @Override
        public Playlist build(ResultSet resultSet) throws HttpException {
            try {
                return new Playlist(resultSet.getLong("playlist_id"),
                        resultSet.getString("name"),
                        resultSet.getLong("user_id"));
            } catch (SQLException e) {
                throw new HttpException(e);
            }
        }
    }
}
