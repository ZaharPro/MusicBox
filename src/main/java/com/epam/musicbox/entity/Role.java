package com.epam.musicbox.entity;

/**
 * The enum Role.
 */
public enum Role implements Entity {

    GUEST(-1),
    USER(1),
    ADMIN(2);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }
}