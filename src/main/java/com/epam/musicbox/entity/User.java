package com.epam.musicbox.entity;

import java.sql.Timestamp;

public class User implements Entity {

    private Long id;
    private String login;
    private String email;
    private String password;
    private Boolean banned;
    private Timestamp registration;

    public User() {
    }

    public User(Long id,
                String login,
                String email,
                String password,
                Boolean banned, Timestamp registration) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.banned = banned;
        this.registration = registration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Timestamp getRegistration() {
        return registration;
    }

    public void setRegistration(Timestamp registration) {
        this.registration = registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (!(id == null ? user.id == null : id.equals(user.id)))
            return false;
        if (!(login == null ? user.login == null : login.equals(user.login)))
            return false;
        if (!(email == null ? user.email == null : email.equals(user.email)))
            return false;
        if (!(password == null ? user.password == null : password.equals(user.password)))
            return false;
        if (!(banned == null ? user.banned == null : banned.equals(user.banned)))
            return false;
        if (!(registration == null ? user.registration == null : registration.equals(user.registration)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (id == null ? 0 : id.hashCode());
        hash = hash * 31 + (login == null ? 0 : login.hashCode());
        hash = hash * 31 + (email == null ? 0 : email.hashCode());
        hash = hash * 31 + (password == null ? 0 : password.hashCode());
        hash = hash * 31 + (banned == null ? 0 : banned.hashCode());
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
                .append(", banned=").append(banned)
                .append(", registration=").append(registration)
                .append('}')
                .toString();
    }
}
