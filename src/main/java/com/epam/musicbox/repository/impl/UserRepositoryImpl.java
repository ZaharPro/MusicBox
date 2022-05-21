package com.epam.musicbox.repository.impl;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.*;
import com.epam.musicbox.repository.UserRepository;
import com.epam.musicbox.util.QueryHelper;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    private static final String SQL_FIND_ALL = """
            SELECT *\040
            FROM users\040
            LIMIT ?, ?""";

    private static final String SQL_FIND_BY_ID = """
            SELECT *\040
            FROM users\040
            WHERE id = ?""";

    private static final String SQL_INSERT_ONE = """
            INSERT INTO users (login, password, email, registration)\040
            VALUES (?, ?, ?, ?, ?, ?)""";

    private static final String SQL_UPDATE_ONE = """
            UPDATE users (id, login, password, email, registration)\040
            SET (?, ?, ?, ?, ?, ?, ?)""";

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM users\040
            WHERE id = ?""";

    private static final String SQL_FIND_BY_LOGIN = """
            SELECT *\040
            FROM users\040
            WHERE login = ?""";

    private static final String SQL_FIND_BY_EMAIL = """
            SELECT *\040
            FROM users\040
            WHERE email = ?""";

    private static final String SQL_FIND_BY_LOGIN_AND_PASSWORD = """
            SELECT *\040
            FROM users\040
            WHERE login = ?\040
            AND password = ?""";

    private static final String SQL_FIND_BY_ROLE = """
            SELECT *\040
            FROM users\040
            JOIN user_roles\040
            ON user_roles.user_id=users.user_id\040
            WHERE user_roles.role_id = ?\040
            LIMIT ?, ?""";

    private static final String SQL_FIND_BY_STATUS = """
            SELECT *\040
            FROM users\040
            JOIN user_statuses\040
            ON user_statuses.user_id=users.user_id\040
            WHERE user_statuses.status_id = ?\040
            LIMIT ?, ?""";

    private static final String SQL_FIND_PLAYLISTS = """
            SELECT *\040
            FROM playlists\040
            JOIN user_playlists\040
            ON user_playlists.playlist_id=playlist.playlist_id\040
            WHERE user_playlists.user_id = ?\040
            LIMIT ?, ?""";

    private static final String SQL_FIND_LIKED_ARTISTS = """
            SELECT *\040
            FROM artists\040
            JOIN user_liked_artists\040
            ON user_liked_artists.artist_id=artists.artist_id\040
            WHERE user_liked_artists.user_id = ?\040
            LIMIT ?, ?""";

    private static final String SQL_FIND_LIKED_ALBUMS = """
            SELECT *\040
            FROM albums\040
            JOIN user_liked_albums\040
            ON user_liked_albums.album_id=albums.album_id\040
            WHERE user_liked_albums.user_id = ?\040
            LIMIT ?, ?""";

    private static final String SQL_FIND_LIKED_TRACKS = """
            SELECT *\040
            FROM tracks\040
            JOIN user_liked_tracks\040
            ON user_liked_tracks.track_id=tracks.track_id\040
            WHERE user_liked_tracks.user_id = ?\040
            LIMIT ?, ?""";

    private static final String SQL_SET_ROLE = """
            INSERT INTO users (login, password, email, registration)\040
            VALUES (?, ?, ?, ?, ?, ?)""";

    private static final String SQL_GET_ROLE = """
            SELECT *\040
            FROM roles\040
            JOIN user_roles\040
            ON user_roles.role_id=roles.role_id\040
            WHERE user_roles.user_id = ?\040
            LIMIT ?, ?""";

    @Override
    public List<User> findAll(int offset, int limit) throws HttpException {
        EntityBuilder<User> builder = new User.Builder();
        return QueryHelper.queryAll(SQL_FIND_ALL, builder, offset, limit);
    }

    @Override
    public Optional<User> findById(Integer id) throws HttpException {
        EntityBuilder<User> builder = new User.Builder();
        return QueryHelper.queryOne(SQL_FIND_BY_ID, builder, id);
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
    public Optional<User> findByLogin(String login) throws HttpException {
        EntityBuilder<User> builder = new User.Builder();
        return QueryHelper.queryOne(SQL_FIND_BY_LOGIN, builder, login);
    }

    @Override
    public Optional<User> findByEmail(String email) throws HttpException {
        EntityBuilder<User> builder = new User.Builder();
        return QueryHelper.queryOne(SQL_FIND_BY_EMAIL, builder, email);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) throws HttpException {
        EntityBuilder<User> builder = new User.Builder();
        return QueryHelper.queryOne(SQL_FIND_BY_LOGIN_AND_PASSWORD, builder, login, password);
    }

    @Override
    public List<User> findAllByRole(Integer roleId, int offset, int limit) throws HttpException {
        EntityBuilder<User> builder = new User.Builder();
        return QueryHelper.queryAll(SQL_FIND_BY_ROLE, builder, roleId, offset, limit);
    }

    @Override
    public List<User> findAllByStatus(Integer statusId, int offset, int limit) throws HttpException {
        EntityBuilder<User> builder = new User.Builder();
        return QueryHelper.queryAll(SQL_FIND_BY_STATUS, builder, statusId, offset, limit);
    }

    @Override
    public List<Playlist> getPlaylists(Integer userId, int offset, int limit) throws HttpException {
        EntityBuilder<Playlist> builder = new Playlist.Builder();
        return QueryHelper.queryAll(SQL_FIND_PLAYLISTS, builder, userId, offset, limit);
    }

    @Override
    public List<Artist> getLikedArtists(Integer userId, int offset, int limit) throws HttpException {
        EntityBuilder<Artist> builder = new Artist.Builder();
        return QueryHelper.queryAll(SQL_FIND_LIKED_ARTISTS, builder, offset, limit);
    }

    @Override
    public List<Album> getLikedAlbums(Integer userId, int offset, int limit) throws HttpException {
        EntityBuilder<Album> builder = new Album.Builder();
        return QueryHelper.queryAll(SQL_FIND_LIKED_ALBUMS, builder, offset, limit);
    }

    @Override
    public List<Track> getLikedTracks(Integer userId, int offset, int limit) throws HttpException {
        EntityBuilder<Track> builder = new Track.Builder();
        return QueryHelper.queryAll(SQL_FIND_LIKED_TRACKS, builder, offset, limit);
    }

    @Override
    public void setRole(Integer userId, Integer roleId) throws HttpException {
        QueryHelper.update(SQL_SET_ROLE, userId, roleId);
    }

    @Override
    public Optional<Role> getRole(Integer userId) throws HttpException {
        EntityBuilder<Role> builder = new Role.Builder();
        return QueryHelper.queryOne(SQL_GET_ROLE, builder, userId);
    }

    @Override
    public void setStatus(Integer userId, Integer statusId) {
        QueryHelper.update(SQL_SET_STATUS, userId, statusId);
    }

    @Override
    public Optional<Status> getStatus(Integer userId) {
        EntityBuilder<Status> builder = new Status.Builder();
        return QueryHelper.queryOne(SQL_GET_STATUS, builder, userId);
    }

    @Override
    public void addPlaylist(Integer userId, Integer playlistId) {
        QueryHelper.update(SQL_ADD_PLAYLIST, userId, playlistId);
    }

    @Override
    public void removePlayList(Integer userId, Integer playlistId) {
        QueryHelper.update(SQL_REMOVE_PLAYLIST, userId, playlistId);
    }

    @Override
    public void likeArtist(Integer userId, Integer artistId) {
        QueryHelper.update(SQL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public void cancelLikeArtist(Integer userId, Integer artistId) {
        QueryHelper.update(SQL_CANCEL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public void likeAlbum(Integer userId, Integer albumId) {
        QueryHelper.update(SQL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public void cancelLikeAlbum(Integer userId, Integer albumId) {
        QueryHelper.update(SQL_CANCEL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public void likeTrack(Integer userId, Integer trackId) {
        QueryHelper.update(SQL_LIKE_TRACK, userId, trackId);
    }

    @Override
    public void cancelLikeTrack(Integer userId, Integer trackId) {
        QueryHelper.update(SQL_CANCEL_LIKE_TRACK, userId, trackId);
    }
}
