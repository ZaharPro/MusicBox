package com.epam.musicbox.entity;

import java.io.Serializable;

/**
 * The type Album.
 */
public class Album implements Entity, Serializable {

    private static final long serialVersionUID = 1827397228126636927L;
    private Long id;
    private String name;
    private String picture;

    /**
     * Instantiates a new Album.
     */
    public Album() {
    }

    /**
     * Instantiates a new Album.
     *
     * @param id      the id
     * @param name    the name
     * @param picture the picture
     */
    public Album(Long id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets picture.
     *
     * @return the picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets picture.
     *
     * @param picture the picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        if (!(id == null ? album.id == null : id.equals(album.id)))
            return false;
        if (!(name == null ? album.name == null : name.equals(album.name)))
            return false;
        return picture == null ? album.picture == null : picture.equals(album.picture);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (id == null ? 0 : id.hashCode());
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (picture == null ? 0 : picture.hashCode());
        return hash;
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
