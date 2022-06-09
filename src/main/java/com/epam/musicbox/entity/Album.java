package com.epam.musicbox.entity;

import com.epam.musicbox.util.ObjectUtils;

public class Album implements Entity<Long> {
    private Long id;
    private String name;
    private String picture;

    public Album() {
    }

    public Album(Long id, String name, String picture) {
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
        Album album = (Album) o;
        return ObjectUtils.equals(id, album.id) &&
                ObjectUtils.equals(name, album.name) &&
                ObjectUtils.equals(picture, album.picture);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id, name, picture);
    }

    @Override
    public String toString() {
        return new StringBuilder("Album{")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", picture='").append(picture).append('\'')
                .append('}')
                .toString();
    }
}
