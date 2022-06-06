package com.epam.musicbox.entity;

import com.epam.musicbox.util.ObjectUtils;

public class Playlist {
    private Long id;
    private String name;
    private String picture;

    public Playlist() {
    }

    public Playlist(Long id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return ObjectUtils.equals(id, playlist.id) &&
               ObjectUtils.equals(name, playlist.name) &&
               ObjectUtils.equals(picture, playlist.picture);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id, name, picture);
    }

    @Override
    public String toString() {
        return new StringBuilder("Playlist{")
                .append("id=").append(id)
                .append("name= '").append(name).append('\'')
                .append("picture= '").append(picture).append('\'')
                .append('}')
                .toString();
    }
}
