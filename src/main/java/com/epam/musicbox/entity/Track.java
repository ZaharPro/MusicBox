package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Track {
    private Integer id;
    private String name;
    private String path;
    private Integer albumId;

    public Track() {
    }

    public Track(Integer id, String name, String path, Integer albumId) {
        this.id = id;
        this.name = name;
        this.albumId = albumId;
        this.path = path;
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

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return ObjectUtils.equals(id, track.id)
                && ObjectUtils.equals(name, track.name)
                && ObjectUtils.equals(path, track.path)
                && ObjectUtils.equals(albumId, track.albumId);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id, name, path, albumId);
    }

    @Override
    public String toString() {
        return new StringBuilder("Track{")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", path='").append(path).append('\'')
                .append(", albumId=").append(albumId)
                .append('}')
                .toString();
    }

    public static class Builder implements EntityBuilder<Track> {
        @Override
        public Track build(ResultSet resultSet) throws HttpException {
            try {
                return new Track(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("path"),
                        resultSet.getInt("album_id"));
            } catch (SQLException e) {
                throw new HttpException(e.getMessage(), e);
            }
        }
    }
}
