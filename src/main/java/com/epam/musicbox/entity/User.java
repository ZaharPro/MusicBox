package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {
    private Integer id;
    private String login;
    private String password;
    private String email;
    private Timestamp registration;

    public User() {
    }

    public User(Integer id,
                String login,
                String password,
                String email,
                Timestamp registration) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                && ObjectUtils.equals(password, user.password)
                && ObjectUtils.equals(email, user.email)
                && ObjectUtils.equals(registration, user.registration);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(id, login, password, email, registration);
    }

    @Override
    public String toString() {
        return new StringBuilder("User{")
                .append("id=").append(id)
                .append(", login='").append(login).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", registration=")
                .append(registration)
                .append('}')
                .toString();
    }

    public static class Builder implements EntityBuilder<User> {
        @Override
        public User build(ResultSet resultSet) throws HttpException {
            try {
                return new User(resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        Timestamp.valueOf(resultSet.getString("registration")));
            } catch (SQLException e) {
                throw new HttpException(e.getMessage(), e);
            }
        }
    }
}
