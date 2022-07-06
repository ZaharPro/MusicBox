package com.epam.musicbox.entity;

import java.io.Serializable;

public class Artist implements Entity, Serializable {

    private static final long serialVersionUID = -6250596912678318522L;
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
        if (!(id == null ? artist.id == null : id.equals(artist.id)))
            return false;
        if (!(name == null ? artist.name == null : name.equals(artist.name)))
            return false;
        return avatar == null ? artist.avatar == null : avatar.equals(artist.avatar);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (id == null ? 0 : id.hashCode());
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (avatar == null ? 0 : avatar.hashCode());
        return hash;
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
