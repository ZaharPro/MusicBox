package com.epam.musicbox.entity;

import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.exception.HttpException;
import jakarta.inject.Singleton;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public enum Role {
    GUEST(0, "guest",
            CommandType.SING_UP,
            CommandType.LOGIN),

    USER(1, "user",
            CommandType.TRACK_GET,
            CommandType.TRACK_GET_BY_ID,

            CommandType.ALBUM_GET,
            CommandType.ALBUM_GET_BY_ID,

            CommandType.ARTIST_GET,
            CommandType.ARTIST_GET_BY_ID,
            CommandType.ARTIST_GET_TRACKS,

            CommandType.PLAYLIST_CREATE,
            CommandType.PLAYLIST_DELETE,
            CommandType.PLAYLIST_GET,
            CommandType.PLAYLIST_GET_BY_ID,
            CommandType.PLAYLIST_GET_TRACKS,
            CommandType.PLAYLIST_ADD_TRACK,
            CommandType.PLAYLIST_REMOVE_TRACK,

            CommandType.USER_GET_LIKED_ALBUMS,
            CommandType.USER_LIKE_ALBUM,
            CommandType.USER_CANCEL_LIKE_ALBUM,
            CommandType.USER_GET_LIKED_ARTISTS,
            CommandType.USER_LIKE_ARTIST,
            CommandType.USER_CANCEL_LIKE_ARTIST,
            CommandType.USER_GET_LIKED_TRACKS,
            CommandType.USER_LIKE_TRACK,
            CommandType.USER_CANCEL_LIKE_TRACK,
            CommandType.USER_GET_PLAYLISTS,
            CommandType.USER_ADD_PLAYLIST,
            CommandType.USER_REMOVE_PLAYLIST),

    ADMIN(2, "admin",
            CommandType.TRACK_SAVE,
            CommandType.TRACK_DELETE,

            CommandType.ALBUM_SAVE,
            CommandType.ALBUM_DELETE,

            CommandType.ARTIST_SAVE,
            CommandType.ARTIST_DELETE,
            CommandType.ARTIST_ADD_TRACK,
            CommandType.ARTIST_REMOVE_TRACK,

            CommandType.USER_GET,
            CommandType.USER_GET_BY_ID,
            CommandType.USER_GET_BY_LOGIN,
            CommandType.USER_GET_BY_EMAIL,
            CommandType.USER_GET_BY_ROLE,
            CommandType.USER_DELETE,
            CommandType.USER_SET_BAN,
            CommandType.USER_SET_ROLE),;

    private final int id;
    private final String name;
    private final Set<CommandType> commandTypes;

    Role(int id, String name, CommandType... commandTypes) {
        this.id = id;
        this.name = name;
        this.commandTypes = Set.of(commandTypes);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isExistCommandType(CommandType type) {
        return this.commandTypes.contains(type);
    }

    public static Role findById(int id) {
        for (Role role : Role.values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        return null;
    }

    public static Role findByName(String name) {
        for (Role role : Role.values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return null;
    }

    @Singleton
    public static class Builder implements EntityBuilder<Role> {
        @Override
        public Role build(ResultSet resultSet) throws HttpException {
            try {
                int roleId = resultSet.getInt("role_id");
                return findById(roleId);
            } catch (SQLException e) {
                throw new HttpException(e);
            }
        }
    }
}


