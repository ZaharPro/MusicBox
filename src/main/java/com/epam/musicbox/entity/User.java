package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.ObjectUtils;
import jakarta.inject.Singleton;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {
    private Integer id;
    private String login;
    private String email;
    private String password;
    private Timestamp registration;

    public User() {
    }

    public User(Integer id,
                String login,
                String email,
                String password,
                Timestamp registration) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.registration = registration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return ObjectUtils.equals(id, user.id)
                && ObjectUtils.equals(login, user.login)
                && ObjectUtils.equals(email, user.email)
                && ObjectUtils.equals(password, user.password)
                && ObjectUtils.equals(registration, user.registration);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id, login, email, password, registration);
    }

    @Override
    public String toString() {
        return new StringBuilder("User{")
                .append("id=").append(id)
                .append(", login='").append(login).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", registration=").append(registration)
                .append('}')
                .toString();
    }

    @Singleton
    public static class Builder implements EntityBuilder<User> {

        private Builder() {
        }

        @Override
        public User build(ResultSet resultSet) throws HttpException {
            try {
                return new User(resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        Timestamp.valueOf(resultSet.getString("registration")));
            } catch (SQLException e) {
                throw new HttpException(e);
            }
        }
    }
}
