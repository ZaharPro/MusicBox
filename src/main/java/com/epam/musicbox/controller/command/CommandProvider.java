package com.epam.musicbox.controller.command;

import com.epam.musicbox.controller.command.impl.album.*;
import com.epam.musicbox.controller.command.impl.artist.*;
import com.epam.musicbox.controller.command.impl.auth.LoginCommand;
import com.epam.musicbox.controller.command.impl.auth.LogoutCommand;
import com.epam.musicbox.controller.command.impl.auth.SingUpCommand;
import com.epam.musicbox.controller.command.impl.page.GoToHomePageCommand;
import com.epam.musicbox.controller.command.impl.page.GoToLoginPageCommand;
import com.epam.musicbox.controller.command.impl.page.GoToSingUpPageCommand;
import com.epam.musicbox.controller.command.impl.playlist.*;
import com.epam.musicbox.controller.command.impl.track.*;
import com.epam.musicbox.controller.command.impl.user.*;

import java.util.EnumMap;
import java.util.Map;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();

    private final Map<CommandType, Command> commands;

    private CommandProvider() {
        Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);

        commands.put(CommandType.GO_TO_SING_UP_PAGE, new GoToSingUpPageCommand());
        commands.put(CommandType.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(CommandType.GO_TO_HOME_PAGE, new GoToHomePageCommand());

        commands.put(CommandType.LOGIN, new LoginCommand());
        commands.put(CommandType.LOGOUT, new LogoutCommand());
        commands.put(CommandType.SING_UP, new SingUpCommand());

        commands.put(CommandType.TRACK_GET, new TrackGetCommand());
        commands.put(CommandType.TRACK_GET_BY_ID, new TrackGetByIdCommand());
        commands.put(CommandType.TRACK_GET_BY_NAME, new TrackGetByNameCommand());
        commands.put(CommandType.TRACK_SAVE, new TrackSaveCommand());
        commands.put(CommandType.TRACK_DELETE, new TrackDeleteCommand());

        commands.put(CommandType.ALBUM_GET, new AlbumGetCommand());
        commands.put(CommandType.ALBUM_GET_BY_ID, new AlbumGetByIdCommand());
        commands.put(CommandType.ALBUM_GET_BY_NAME, new AlbumGetByNameCommand());
        commands.put(CommandType.ALBUM_SAVE, new AlbumSaveCommand());
        commands.put(CommandType.ALBUM_DELETE, new AlbumDeleteCommand());

        commands.put(CommandType.ARTIST_GET, new ArtistGetCommand());
        commands.put(CommandType.ARTIST_GET_BY_ID, new ArtistGetByIdCommand());
        commands.put(CommandType.ARTIST_GET_BY_NAME, new ArtistGetByNameCommand());
        commands.put(CommandType.ARTIST_SAVE, new ArtistSaveCommand());
        commands.put(CommandType.ARTIST_DELETE, new ArtistDeleteCommand());
        commands.put(CommandType.ARTIST_GET_TRACKS, new ArtistGetTracksCommand());
        commands.put(CommandType.ARTIST_ADD_TRACK, new ArtistAddTrackCommand());
        commands.put(CommandType.ARTIST_REMOVE_TRACK, new ArtistRemoveTrackCommand());

        commands.put(CommandType.PLAYLIST_GET, new PlaylistGetCommand());
        commands.put(CommandType.PLAYLIST_GET_BY_ID, new PlaylistGetByIdCommand());
        commands.put(CommandType.PLAYLIST_GET_BY_NAME, new PlaylistGetByNameCommand());
        commands.put(CommandType.PLAYLIST_SAVE, new PlaylistSaveCommand());
        commands.put(CommandType.PLAYLIST_DELETE, new PlaylistDeleteCommand());
        commands.put(CommandType.PLAYLIST_GET_TRACKS, new PlaylistGetTracksCommand());
        commands.put(CommandType.PLAYLIST_ADD_TRACK, new PlaylistAddTrackCommand());
        commands.put(CommandType.PLAYLIST_REMOVE_TRACK, new PlaylistRemoveTrackCommand());

        commands.put(CommandType.USER_GET, new UserGetCommand());
        commands.put(CommandType.USER_GET_BY_ID, new UserGetByIdCommand());
        commands.put(CommandType.USER_DELETE, new UserDeleteCommand());
        commands.put(CommandType.USER_GET_BY_LOGIN, new UserGetByLoginCommand());
        commands.put(CommandType.USER_GET_BY_EMAIL, new UserGetByEmailCommand());
        commands.put(CommandType.USER_GET_BY_ROLE, new UserGetByRoleCommand());
        commands.put(CommandType.USER_GET_LIKED_ALBUMS, new UserGetLikedAlbumsCommand());
        commands.put(CommandType.USER_LIKE_ALBUM, new UserLikeAlbumCommand());
        commands.put(CommandType.USER_CANCEL_LIKE_ALBUM, new UserCancelLikeAlbumCommand());
        commands.put(CommandType.USER_GET_LIKED_ARTISTS, new UserGetLikedArtistsCommand());
        commands.put(CommandType.USER_LIKE_ARTIST, new UserLikeArtistCommand());
        commands.put(CommandType.USER_CANCEL_LIKE_ARTIST, new UserCancelLikeArtistCommand());
        commands.put(CommandType.USER_GET_LIKED_TRACKS, new UserGetLikedTracksCommand());
        commands.put(CommandType.USER_LIKE_TRACK, new UserLikeTrackCommand());
        commands.put(CommandType.USER_CANCEL_LIKE_TRACK, new UserCancelLikeTrackCommand());
        commands.put(CommandType.USER_GET_PLAYLISTS, new UserGetPlaylistsCommand());
        commands.put(CommandType.USER_ADD_PLAYLIST, new UserAddPlaylistCommand());
        commands.put(CommandType.USER_REMOVE_PLAYLIST, new UserRemovePlaylistCommand());

        commands.put(CommandType.USER_SET_BAN, new UserSetBanCommand());
        commands.put(CommandType.USER_SET_ROLE, new UserSetRoleCommand());

        this.commands = commands;
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Command get(CommandType type) {
        return commands.get(type);
    }
}











