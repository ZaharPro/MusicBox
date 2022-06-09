package com.epam.musicbox.controller.filter.access;

import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Role;

import java.util.*;

public class RoleRights {
    private static final RoleRights instance = new RoleRights();

    private final Map<Role, Set<CommandType>> map;

    private RoleRights() {
        Map<Role, Set<CommandType>> map = new EnumMap<>(Role.class);

        map.put(Role.GUEST, setOf(
                CommandType.GO_TO_LOGIN_PAGE,
                CommandType.GO_TO_SIGN_UP_PAGE,
                CommandType.GO_TO_HOME_PAGE,

                CommandType.SIGN_UP,
                CommandType.LOGIN));

        map.put(Role.USER, setOf(
                CommandType.LOGOUT,
                CommandType.CHANGE_PASSWORD,
                CommandType.SEARCH,
                CommandType.GO_TO_HOME_PAGE,
                CommandType.GO_TO_CHANGE_PASSWORD_PAGE,
                CommandType.GO_TO_EDIT_PLAYLIST_PAGE,

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
                CommandType.USER_REMOVE_PLAYLIST));

        map.put(Role.ADMIN, EnumSet.allOf(CommandType.class));

        this.map = map;
    }

    public static RoleRights getInstance() {
        return instance;
    }

    private Set<CommandType> setOf(CommandType... commandTypes) {
        return EnumSet.copyOf(Arrays.asList(commandTypes));
    }

    public boolean isExistCommandType(Role role, CommandType type) {
        Set<CommandType> commands = map.get(role);
        return commands != null && commands.contains(type);
    }
}