package com.epam.musicbox.controller.command;

public enum CommandType {
    //auth
    LOGIN("login"),
    LOGOUT("logout"),
    SING_UP("singup"),

    //artist
    ARTIST_GET("artistget"),
    ARTIST_SAVE("artistsave"),
    ARTIST_DELETE("artistdelete"),

    ARTIST_GET_TRACKS("artistgettracks"),
    ARTIST_ADD_TRACK("artistaddtrack"),
    ARTIST_REMOVE_TRACK("artistremovetrack"),

    //playlist
    PLAYLIST_CREATE("playlistcreate"),
    PLAYLIST_DELETE("playlistdelete"),
    PLAYLIST_GET("playlistget"),

    PLAYLIST_GET_TRACKS("playlistgettracks"),
    PLAYLIST_ADD_TRACK("playlistaddtrack"),
    PLAYLIST_REMOVE_TRACK("playlistremovetrack"),

    //user
    USER_GET("userget"),
    USER_GET_BY_LOGIN("usergetbylogin"),
    USER_GET_BY_EMAIL("usergetbyemail"),
    USER_GET_BY_ROLE("usergetbyrole"),
    USER_DELETE("userdelete"),

    USER_GET_LIKED_ALBUMS("usergetlikedalbums"),
    USER_LIKE_ALBUM("userlikealbum"),
    USER_CANCEL_LIKE_ALBUM("usercancellikealbum"),

    USER_GET_LIKED_ARTISTS("usergetlikedartists"),
    USER_LIKE_ARTIST("userlikeartist"),
    USER_CANCEL_LIKE_ARTIST("usercancellikeartist"),

    USER_GET_LIKED_TRACKS("usergetlikedtracks"),
    USER_LIKE_TRACK("userliketrack"),
    USER_CANCEL_LIKE_TRACK("usercancelliketrack"),

    USER_GET_PLAYLISTS("usergetplaylists"),
    USER_ADD_PLAYLIST("useraddplaylist"),
    USER_REMOVE_PLAYLIST("userremoveplaylist"),

    //admin
    ADMIN_SET_BAN("usersetban"),
    ADMIN_SET_ROLE("usersetrole");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CommandType of(String name) {
        for (CommandType type : CommandType.values()) {
            if (name.equals(type.getName())) {
                return type;
            }
        }
        return null;
    }
}
