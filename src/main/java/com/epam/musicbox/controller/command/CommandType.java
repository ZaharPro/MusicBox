package com.epam.musicbox.controller.command;

/**
 * The enum Command type.
 */
public enum CommandType {
    //page
    SIGN_UP_PAGE("sign-up-page"),
    LOGIN_PAGE("login-page"),
    CHANGE_PASSWORD_PAGE("change-password-page"),
    HOME_PAGE("home-page"),
    ADMIN_PAGE("admin-page"),
    PROFILE_PAGE("profile-page"),
    EDIT_ARTIST_PAGE("edit-artist-page"),
    EDIT_ALBUM_PAGE("edit-album-page"),
    EDIT_TRACK_PAGE("edit-track-page"),
    EDIT_PLAYLIST_PAGE("edit-playlist-page"),
    SEARCH("search"),
    CHANGE_LOCALE("change-locale"),

    //auth
    LOGIN("login"),
    LOGOUT("logout"),
    SIGN_UP("sign-up"),
    CHANGE_PASSWORD("change-password"),

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

    ARTIST_ADD_TRACK("artist-add-track"),
    ARTIST_REMOVE_TRACK("artist-remove-track"),

    //playlist
    PLAYLIST_GET("playlist-get"),
    PLAYLIST_GET_BY_ID("playlist-get-by-id"),
    PLAYLIST_GET_BY_NAME("playlist-get-by-name"),
    PLAYLIST_SAVE("playlist-save"),
    PLAYLIST_DELETE("playlist-delete"),

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
    USER_MARK_LIKED_ALBUM("user-mark-liked-album"),
    USER_UNMARK_LIKED_ALBUM("user-unmark-liked-album"),

    USER_GET_LIKED_ARTISTS("user-get-liked-artists"),
    USER_MARK_LIKED_ARTIST("user-mark-liked-artist"),
    USER_UNMARK_LIKED_ARTIST("user-unmark-liked-artist"),

    USER_GET_LIKED_TRACKS("user-get-liked-tracks"),
    USER_MARK_LIKED_TRACK("user-mark-liked-track"),
    USER_UNMARK_LIKED_TRACK("user-unmark-liked-track"),

    USER_GET_PLAYLISTS("user-get-playlists"),

    USER_SET_BAN("user-set-ban"),
    USER_SET_ROLE("user-set-role");

    private final String name;

    CommandType(String name) {
        this.name = name;
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