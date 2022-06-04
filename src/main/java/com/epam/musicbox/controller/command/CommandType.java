package com.epam.musicbox.controller.command;

public enum CommandType {
    //page
    GO_TO_SIGN_UP_PAGE("sign-up-page"),
    GO_TO_LOGIN_PAGE("login-page"),
    GO_TO_HOME_PAGE("home-page"),
    SEARCH("search"),

    //auth
    LOGIN("login"),
    LOGOUT("logout"),
    SIGN_UP("sign-up"),
    CHANGE_PASSWORD("change-pass"),

    //track
    TRACK_GET("track-get"),
    TRACK_GET_BY_ID("track-get-by-id"),
    TRACK_GET_BY_NAME("track-get-by-name"),
    TRACK_SAVE("track-save"),
    TRACK_DELETE("track-delete"),

    //album
    ALBUM_GET("album-get"),
    ALBUM_GET_BY_ID("album-get-by-id"),
    ALBUM_GET_BY_NAME("album-get-by-name"),
    ALBUM_SAVE("album-save"),
    ALBUM_DELETE("album-delete"),

    //artist
    ARTIST_GET("artist-get"),
    ARTIST_GET_BY_ID("artist-get-by-id"),
    ARTIST_GET_BY_NAME("artist-get-by-name"),
    ARTIST_SAVE("artist-save"),
    ARTIST_DELETE("artist-delete"),

    ARTIST_GET_TRACKS("artist-get-tracks"),
    ARTIST_ADD_TRACK("artist-add-track"),
    ARTIST_REMOVE_TRACK("artist-remove-track"),

    //playlist
    PLAYLIST_GET("playlist-get"),
    PLAYLIST_GET_BY_ID("playlist-get-by-id"),
    PLAYLIST_GET_BY_NAME("playlist-get-by-name"),
    PLAYLIST_SAVE("playlist-save"),
    PLAYLIST_DELETE("playlist-delete"),

    PLAYLIST_GET_TRACKS("playlist-get-tracks"),
    PLAYLIST_ADD_TRACK("playlist-add-track"),
    PLAYLIST_REMOVE_TRACK("playlist-remove-track"),

    //user
    USER_GET("user-get"),
    USER_DELETE("user-delete"),

    USER_GET_BY_ID("user-get-by-id"),
    USER_GET_BY_LOGIN("user-get-by-login"),
    USER_GET_BY_EMAIL("user-get-by-email"),
    USER_GET_BY_ROLE("user-get-by-role"),

    USER_GET_LIKED_ALBUMS("user-get-liked-albums"),
    USER_LIKE_ALBUM("user-like-album"),
    USER_CANCEL_LIKE_ALBUM("user-cancel-like-album"),

    USER_GET_LIKED_ARTISTS("user-get-liked-artists"),
    USER_LIKE_ARTIST("user-like-artist"),
    USER_CANCEL_LIKE_ARTIST("user-cancel-like-artist"),

    USER_GET_LIKED_TRACKS("user-get-liked-tracks"),
    USER_LIKE_TRACK("user-like-track"),
    USER_CANCEL_LIKE_TRACK("user-cancel-like-track"),

    USER_GET_PLAYLISTS("user-get-playlists"),
    USER_ADD_PLAYLIST("user-add-playlist"),
    USER_REMOVE_PLAYLIST("user-remove-playlist"),

    USER_SET_BAN("user-set-ban"),
    USER_SET_ROLE("user-set-role");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CommandType of(String name) {
        if (name == null)
            return null;
        for (CommandType type : CommandType.values()) {
            if (name.equals(type.getName())) {
                return type;
            }
        }
        return null;
    }
}