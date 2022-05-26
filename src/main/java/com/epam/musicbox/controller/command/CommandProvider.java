package com.epam.musicbox.controller.command;

import com.epam.musicbox.controller.command.impl.admin.AdminSetBanCommand;
import com.epam.musicbox.controller.command.impl.admin.AdminSetRoleCommand;
import com.epam.musicbox.controller.command.impl.artist.*;
import com.epam.musicbox.controller.command.impl.auth.LoginCommand;
import com.epam.musicbox.controller.command.impl.auth.LogoutCommand;
import com.epam.musicbox.controller.command.impl.auth.SingUpCommand;
import com.epam.musicbox.controller.command.impl.playlist.*;
import com.epam.musicbox.controller.command.impl.user.*;
import jakarta.inject.Singleton;

import java.util.EnumMap;
import java.util.Map;

@Singleton
public class CommandProvider {
    private final Map<CommandType, Command> commands;

    private CommandProvider() {
        Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);

        commands.put(CommandType.LOGIN, new LoginCommand());
        commands.put(CommandType.LOGOUT, new LogoutCommand());
        commands.put(CommandType.SING_UP, new SingUpCommand());

        commands.put(CommandType.ARTIST_GET, new ArtistGetCommand());
        commands.put(CommandType.ARTIST_SAVE, new ArtistSaveCommand());
        commands.put(CommandType.ARTIST_DELETE, new ArtistDeleteCommand());
        commands.put(CommandType.ARTIST_GET_TRACKS, new ArtistGetCommand());
        commands.put(CommandType.ARTIST_ADD_TRACK, new ArtistAddTrackCommand());
        commands.put(CommandType.ARTIST_REMOVE_TRACK, new ArtistRemoveTrackCommand());

        commands.put(CommandType.PLAYLIST_CREATE, new PlaylistCreateCommand());
        commands.put(CommandType.PLAYLIST_DELETE, new PlaylistDeleteCommand());
        commands.put(CommandType.PLAYLIST_GET, new PlaylistGetCommand());
        commands.put(CommandType.PLAYLIST_GET_TRACKS, new PlaylistGetTracksCommand());
        commands.put(CommandType.PLAYLIST_ADD_TRACK, new PlaylistAddTrackCommand());
        commands.put(CommandType.PLAYLIST_REMOVE_TRACK, new PlaylistRemoveTrackCommand());

        commands.put(CommandType.USER_GET, new UserGetCommand());
        commands.put(CommandType.USER_GET_BY_LOGIN, new UserGetByLoginCommand());
        commands.put(CommandType.USER_GET_BY_EMAIL, new UserGetByEmailCommand());
        commands.put(CommandType.USER_GET_BY_ROLE, new UserGetByRoleCommand());
        commands.put(CommandType.USER_DELETE, new UserDeleteCommand());
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

        commands.put(CommandType.ADMIN_SET_BAN, new AdminSetBanCommand());
        commands.put(CommandType.ADMIN_SET_ROLE, new AdminSetRoleCommand());

        this.commands = commands;
    }

    public Command get(CommandType type) {
        return commands.get(type);
    }
}
