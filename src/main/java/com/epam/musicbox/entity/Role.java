package com.epam.musicbox.entity;

/**
 * The enum Role.
 */
public enum Role implements Entity {

    GUEST(-1, "guest"),
    USER(1, "user"),
    ADMIN(2, "admin");

    private final int id;
    private final String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
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
     * Find by name role.
     *
     * @param name the name
     * @return the role
     */
    public static Role findByName(String name) {
        for (Role role : Role.values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return null;
    }
}