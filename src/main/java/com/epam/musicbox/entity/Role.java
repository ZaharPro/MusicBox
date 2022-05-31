package com.epam.musicbox.entity;

import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.exception.HttpException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum Role {
    USER(0, "user",
            CommandType.GO_TO_LOGIN_PAGE,
            CommandType.GO_TO_SING_UP_PAGE,
            CommandType.GO_TO_HOME_PAGE,

            CommandType.TRACK_GET,
            CommandType.TRACK_GET_BY_ID,
            CommandType.TRACK_GET_BY_NAME,

            CommandType.ALBUM_GET,
            CommandType.ALBUM_GET_BY_ID,
            CommandType.ALBUM_GET_BY_NAME,

            CommandType.ARTIST_GET,
            CommandType.ARTIST_GET_BY_ID,
            CommandType.ARTIST_GET_BY_NAME,
            CommandType.ARTIST_GET_TRACKS,

            CommandType.PLAYLIST_SAVE,
            CommandType.PLAYLIST_DELETE,
            CommandType.PLAYLIST_GET,
            CommandType.PLAYLIST_GET_BY_ID,
            CommandType.PLAYLIST_GET_BY_NAME,
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

    ADMIN(1, "admin"),

    GUEST(Integer.MAX_VALUE, null,
            CommandType.GO_TO_LOGIN_PAGE,
            CommandType.GO_TO_SING_UP_PAGE,
            CommandType.GO_TO_HOME_PAGE,
            CommandType.SING_UP,
            CommandType.LOGIN);

    private final int id;
    private final String name;
    private final Set<CommandType> commandTypes;

    Role(int id, String name, CommandType... commandTypes) {
        this.id = id;
        this.name = name;
        this.commandTypes = commandTypes.length == 0 ?
                Collections.emptySet() :
                EnumSet.copyOf(Arrays.asList(commandTypes));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isExistCommandType(CommandType type) {
        return type != null &&
                (this == ADMIN || this.commandTypes.contains(type));
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

    public static class Builder implements EntityBuilder<Role> {
        private static final Builder instance = new Builder();

        private Builder() {
        }

        public static Builder getInstance() {
            return instance;
        }

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


