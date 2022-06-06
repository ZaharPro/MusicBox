package com.epam.musicbox.entity;

import com.epam.musicbox.util.ObjectUtils;

public class Track {
    private Long id;
    private String name;
    private String path;
    private Long albumId;

    public Track() {
    }

    public Track(Long id, String name, String path, Long albumId) {
        this.id = id;
        this.name = name;
        this.albumId = albumId;
        this.path = path;
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

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
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
}
