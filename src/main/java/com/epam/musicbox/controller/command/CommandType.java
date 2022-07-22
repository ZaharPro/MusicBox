package com.epam.musicbox.controller.command;

import java.util.Locale;

/**
 * The enum Command type.
 */
public enum CommandType {

    //page
    SIGN_UP_PAGE,
    LOGIN_PAGE,
    CHANGE_PASSWORD_PAGE,
    HOME_PAGE,
    ADMIN_PAGE,
    PROFILE_PAGE,
    EDIT_ARTIST_PAGE,
    EDIT_ALBUM_PAGE,
    EDIT_TRACK_PAGE,
    EDIT_PLAYLIST_PAGE,

    //common
    SEARCH,
    CHANGE_LOCALE,

    //auth
    LOGIN,
    LOGOUT,
    SIGN_UP,
    CHANGE_PASSWORD,

    //track
    TRACK_GET,
    TRACK_GET_BY_ID,
    TRACK_GET_BY_NAME,
    TRACK_SAVE,
    TRACK_DELETE,

    //album
    ALBUM_GET,
    ALBUM_GET_BY_ID,
    ALBUM_GET_BY_NAME,
    ALBUM_SAVE,
    ALBUM_DELETE,

    //artist
    ARTIST_GET,
    ARTIST_GET_BY_ID,
    ARTIST_GET_BY_NAME,
    ARTIST_SAVE,
    ARTIST_DELETE,
    ARTIST_ADD_TRACK,
    ARTIST_REMOVE_TRACK,

    //playlist
    PLAYLIST_GET,
    PLAYLIST_GET_BY_ID,
    PLAYLIST_GET_BY_NAME,
    PLAYLIST_SAVE,
    PLAYLIST_DELETE,
    PLAYLIST_ADD_TRACK,
    PLAYLIST_REMOVE_TRACK,

    //user
    USER_GET,
    USER_DELETE,
    USER_GET_BY_ID,
    USER_GET_BY_LOGIN,
    USER_GET_BY_EMAIL,
    USER_GET_BY_ROLE,
    USER_GET_LIKED_ALBUMS,
    USER_MARK_LIKED_ALBUM,
    USER_UNMARK_LIKED_ALBUM,
    USER_GET_LIKED_ARTISTS,
    USER_MARK_LIKED_ARTIST,
    USER_UNMARK_LIKED_ARTIST,
    USER_GET_LIKED_TRACKS,
    USER_MARK_LIKED_TRACK,
    USER_UNMARK_LIKED_TRACK,
    USER_GET_PLAYLISTS,
    USER_SET_BAN,
    USER_SET_ROLE;

    private final String name;

    CommandType() {
        this.name = name().toLowerCase(Locale.ROOT).replace('_', '-');
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
     * Find by name command type.
     *
     * @param name the name
     * @return the command type
     */
    public static CommandType findByName(String name) {
        if (name != null) {
            for (CommandType value : CommandType.values()) {
                if (value.getName().equals(name)) {
                    return value;
                }
            }
        }
        return null;
    }
}