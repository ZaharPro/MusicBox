package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Playlist {
    private Integer id;

    public Playlist() {
    }

    public Playlist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return ObjectUtils.equals(id, playlist.id);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id);
    }

    @Override
    public String toString() {
        return new StringBuilder("Playlist{")
                .append("id=").append(id)
                .append('}')
                .toString();
    }

    public static class Builder implements EntityBuilder<Playlist> {
        @Override
        public Playlist build(ResultSet resultSet) throws HttpException {
            try {
                return new Playlist(resultSet.getInt("id"));
            } catch (SQLException e) {
                throw new HttpException(e.getMessage(), e);
            }
        }
    }
}
