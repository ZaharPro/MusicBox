package com.epam.musicbox.repository.impl;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.*;
import com.epam.musicbox.repository.UserRepository;
import com.epam.musicbox.util.QueryHelper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    private static final String SQL_FIND_ALL = ;
    private static final String SQL_FIND_BY_ID = ;
    private static final String SQL_INSERT_ONE = ;
    private static final String SQL_UPDATE_ONE = ;
    private static final String SQL_DELETE_BY_ID = ;

    private static final String SQL_FIND_BY_LOGIN = ;
    private static final String SQL_FIND_BY_EMAIL = ;
    private static final String SQL_FIND_BY_ROLE = ;

    private static final String SQL_FIND_PLAYLISTS = ;
    private static final String SQL_FIND_LIKED_ARTISTS = ;
    private static final String SQL_FIND_LIKED_ALBUMS = ;
    private static final String SQL_FIND_LIKED_TRACKS = ;

    private static final String SQL_SET_ROLE = ;
    private static final String SQL_GET_ROLE = ;

    private static final String SQL_ADD_PLAYLIST = ;
    private static final String SQL_REMOVE_PLAYLIST = ;

    private static final String SQL_LIKE_ARTIST = ;
    private static final String SQL_CANCEL_LIKE_ARTIST = ;

    private static final String SQL_LIKE_ALBUM = ;
    private static final String SQL_CANCEL_LIKE_ALBUM = ;

    private static final String SQL_LIKE_TRACK = ;
    private static final String SQL_CANCEL_LIKE_TRACK = ;


    @Inject
    private User.Builder userEntityBuilder;

    @Inject
    private Playlist.Builder playlistEntityBuilder;

    @Inject
    private Artist.Builder artistEntityBuilder;

    @Inject
    private Album.Builder albumEntityBuilder;

    @Inject
    private Track.Builder trackEntityBuilder;

    @Inject
    private Role.Builder roleEntityBuilder;

    @Override
    public List<User> findAll(int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_ALL, userEntityBuilder, offset, limit);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, userEntityBuilder, id);
    }

    @Override
    public void save(User user) throws HttpException {
        Integer userId = user.getId();
        if (userId == null) {
            QueryHelper.update(SQL_INSERT_ONE,
                    user.getLogin(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getRegistration());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    user.getId(),
                    user.getLogin(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getRegistration());
        }
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return QueryHelper.queryOne(SQL_FIND_BY_LOGIN, userEntityBuilder, login);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return QueryHelper.queryOne(SQL_FIND_BY_EMAIL, userEntityBuilder, email);
    }

    @Override
    public List<User> findAllByRole(Integer roleId, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_BY_ROLE, userEntityBuilder, roleId, offset, limit);
    }

    @Override
    public List<Playlist> getPlaylists(Integer userId, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_PLAYLISTS, playlistEntityBuilder, userId, offset, limit);
    }

    @Override
    public List<Artist> getLikedArtists(Integer userId, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_ARTISTS, artistEntityBuilder, offset, limit);
    }

    @Override
    public List<Album> getLikedAlbums(Integer userId, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_ALBUMS, albumEntityBuilder, offset, limit);
    }

    @Override
    public List<Track> getLikedTracks(Integer userId, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_TRACKS, trackEntityBuilder, offset, limit);
    }

    @Override
    public void setRole(Integer userId, Integer roleId) throws HttpException {
        QueryHelper.update(SQL_SET_ROLE, userId, roleId);
    }

    @Override
    public Optional<Role> getRole(Integer userId) {
        return QueryHelper.queryOne(SQL_GET_ROLE, roleEntityBuilder, userId);
    }

    @Override
    public void addPlaylist(Integer userId, Integer playlistId) throws HttpException {
        QueryHelper.update(SQL_ADD_PLAYLIST, userId, playlistId);
    }

    @Override
    public void removePlayList(Integer userId, Integer playlistId) throws HttpException {
        QueryHelper.update(SQL_REMOVE_PLAYLIST, userId, playlistId);
    }

    @Override
    public void likeArtist(Integer userId, Integer artistId) throws HttpException {
        QueryHelper.update(SQL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public void cancelLikeArtist(Integer userId, Integer artistId) throws HttpException {
        QueryHelper.update(SQL_CANCEL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public void likeAlbum(Integer userId, Integer albumId) throws HttpException {
        QueryHelper.update(SQL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public void cancelLikeAlbum(Integer userId, Integer albumId) throws HttpException {
        QueryHelper.update(SQL_CANCEL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public void likeTrack(Integer userId, Integer trackId) throws HttpException {
        QueryHelper.update(SQL_LIKE_TRACK, userId, trackId);
    }

    @Override
    public void cancelLikeTrack(Integer userId, Integer trackId) throws HttpException {
        QueryHelper.update(SQL_CANCEL_LIKE_TRACK, userId, trackId);
    }
}
