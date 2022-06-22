package com.epam.musicbox.entity;

public class Playlist implements Entity {

    private Long id;
    private String name;
    private String picture;
    private long userId;

    public Playlist() {
    }

    public Playlist(Long id, String name, String picture, long userId) {
        this.id = id;
        this.name = name;
        this.picture = picture;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        if (!(id == null ? playlist.id == null : id.equals(playlist.id)))
            return false;
        if (!(name == null ? playlist.name == null : name.equals(playlist.name)))
            return false;
        if (!(picture == null ? playlist.picture == null : picture.equals(playlist.picture)))
            return false;
        return userId == playlist.userId;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (id == null ? 0 : id.hashCode());
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (picture == null ? 0 : picture.hashCode());
        hash = hash * 31 + Long.hashCode(userId);
        return hash;
    }

    @Override
    public String toString() {
        return new StringBuilder("Playlist{")
                .append("id=").append(id)
                .append("name= '").append(name).append('\'')
                .append("picture= '").append(picture).append('\'')
                .append("userId= '").append(userId).append('\'')
                .append('}')
                .toString();
    }
}
