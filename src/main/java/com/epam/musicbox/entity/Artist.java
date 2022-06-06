package com.epam.musicbox.entity;

import com.epam.musicbox.util.ObjectUtils;

public class Artist {
    private Long id;
    private String name;
    private String avatar;

    public Artist() {
    }

    public Artist(Long id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
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
}
