package com.epam.musicbox.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The type User.
 */
public class User implements Entity, Serializable {

    private static final long serialVersionUID = -2088667589661182477L;
    private Long id;
    private String login;
    private String email;
    private String password;
    private Role role;
    private boolean banned;
    private Timestamp registration;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param id           the id
     * @param login        the login
     * @param email        the email
     * @param password     the password
     * @param role         the role
     * @param banned       the banned
     * @param registration the registration
     */
    public User(Long id, String login, String email, String password, Role role, boolean banned, Timestamp registration) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.banned = banned;
        this.role = role;
        this.registration = registration;
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
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets banned.
     *
     * @return the banned
     */
    public boolean getBanned() {
        return banned;
    }

    /**
     * Sets banned.
     *
     * @param banned the banned
     */
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    /**
     * Gets registration.
     *
     * @return the registration
     */
    public Timestamp getRegistration() {
        return registration;
    }

    /**
     * Sets registration.
     *
     * @param registration the registration
     */
    public void setRegistration(Timestamp registration) {
        this.registration = registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (!(id == null ? user.id == null : id.equals(user.id))) return false;
        if (!(login == null ? user.login == null : login.equals(user.login))) return false;
        if (!(email == null ? user.email == null : email.equals(user.email))) return false;
        if (!(password == null ? user.password == null : password.equals(user.password))) return false;
        if (banned != user.banned) return false;
        if (!(role == null ? user.role == null : role.equals(user.role))) return false;
        return registration == null ? user.registration == null : registration.equals(user.registration);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (id == null ? 0 : id.hashCode());
        hash = hash * 31 + (login == null ? 0 : login.hashCode());
        hash = hash * 31 + (email == null ? 0 : email.hashCode());
        hash = hash * 31 + (password == null ? 0 : password.hashCode());
        hash = hash * 31 + Boolean.hashCode(banned);
        hash = hash * 31 + (registration == null ? 0 : registration.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return new StringBuilder("User{")
                .append("id=").append(id)
                .append(", login='").append(login).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", role=").append(role)
                .append(", banned=").append(banned)
                .append(", registration=").append(registration)
                .append('}')
                .toString();
    }
}
