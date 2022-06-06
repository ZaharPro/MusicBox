package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.rowmapper.*;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.entity.*;
import com.epam.musicbox.repository.UserRepository;
import com.epam.musicbox.util.QueryHelper;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM users " +
                                               "ORDER BY name " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM users " +
                                                 "WHERE user_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO users (login, email, password, banned) " +
                                                 "VALUES (?,?,?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE users (login, email, password, banned) " +
                                                 "SET login=? email=? password=? banned=? " +
                                                 "WHERE user_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM users " +
                                                   "WHERE user_id=?";

    private static final String SQL_FIND_BY_LOGIN = "SELECT * " +
                                                    "FROM users " +
                                                    "WHERE login=?";

    private static final String SQL_FIND_BY_EMAIL = "SELECT * " +
                                                    "FROM users " +
                                                    "WHERE email=?";

    private static final String SQL_FIND_BY_ROLE = "SELECT * " +
                                                   "FROM users " +
                                                   "JOIN user_roles " +
                                                   "ON user_roles.role_id = roles.role_id " +
                                                   "WHERE user_roles.user_id=? " +
                                                   "LIMIT ?,?";

    private static final String SQL_SET_ROLE = "INSERT INTO user_roles (user_id, role_id) " +
                                               "VALUES (?,?)";

    private static final String SQL_GET_ROLE = "SELECT * " +
                                               "FROM roles " +
                                               "JOIN user_roles " +
                                               "ON user_roles.role_id = roles.role_id " +
                                               "WHERE user_roles.user_id=?";

    private static final String SQL_FIND_PLAYLISTS = "SELECT * " +
                                                     "FROM playlists " +
                                                     "JOIN user_playlists " +
                                                     "ON user_playlists.playlist_id = playlist.playlist_id " +
                                                     "WHERE user_playlists.user_id=?";

    private static final String SQL_ADD_PLAYLIST = "INSERT INTO user_playlists (user_id, playlist_id) " +
                                                   "VALUES (?,?)";

    private static final String SQL_REMOVE_PLAYLIST = "DELETE FROM user_playlists " +
                                                      "WHERE user_id=? AND playlist_id=?";

    private static final String SQL_FIND_LIKED_ARTISTS = "SELECT * " +
                                                         "FROM tracks " +
                                                         "JOIN user_liked_artists " +
                                                         "ON user_liked_artists.track_id = tracks.track_id " +
                                                         "WHERE user_liked_artists.user_id=?";

    private static final String SQL_LIKE_ARTIST = "INSERT INTO user_liked_artists (user_id, track_id) " +
                                                  "VALUES (?,?)";

    private static final String SQL_CANCEL_LIKE_ARTIST = "DELETE FROM user_liked_artists " +
                                                         "WHERE user_id=? AND track_id=?";

    private static final String SQL_FIND_LIKED_ALBUMS = "SELECT * " +
                                                        "FROM tracks " +
                                                        "JOIN user_liked_albums " +
                                                        "ON user_liked_albums.track_id = tracks.track_id " +
                                                        "WHERE user_liked_albums.user_id=?";

    private static final String SQL_LIKE_ALBUM = "INSERT INTO user_liked_albums (user_id, track_id) " +
                                                 "VALUES (?,?)";

    private static final String SQL_CANCEL_LIKE_ALBUM = "DELETE FROM user_liked_albums " +
                                                        "WHERE user_id=? AND track_id=?";

    private static final String SQL_FIND_LIKED_TRACKS = "SELECT * " +
                                                        "FROM tracks " +
                                                        "JOIN user_liked_tracks " +
                                                        "ON user_liked_tracks.track_id = tracks.track_id " +
                                                        "WHERE user_liked_tracks.user_id=?";

    private static final String SQL_LIKE_TRACK = "INSERT INTO user_liked_tracks (user_id, track_id) " +
                                                 "VALUES (?,?)";

    private static final String SQL_CANCEL_LIKE_TRACK = "DELETE FROM user_liked_tracks " +
                                                        "WHERE user_id=? AND track_id=?";

    public static final UserRepositoryImpl instance = new UserRepositoryImpl();

    private final UserRowMapper userRowMapper = UserRowMapper.getInstance();

    private final PlaylistRowMapper playlistRowMapper = PlaylistRowMapper.getInstance();

    private final ArtistRowMapper artistRowMapper = ArtistRowMapper.getInstance();

    private final AlbumRowMapper albumRowMapper = AlbumRowMapper.getInstance();

    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();

    private final RoleRowMapper roleRowMapper = RoleRowMapper.getInstance();

    public static UserRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, userRowMapper, offset, limit);
    }

    @Override
    public Optional<User> findById(Long id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, userRowMapper, id);
    }

    @Override
    public long save(User user) throws RepositoryException {
        Long userId = user.getId();
        if (userId == null) {
            return QueryHelper.insert(SQL_INSERT_ONE,
                    user.getLogin(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getBanned());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    user.getLogin(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getBanned(),
                    userId);
            return userId;
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return QueryHelper.queryOne(SQL_FIND_BY_LOGIN, userRowMapper, login);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return QueryHelper.queryOne(SQL_FIND_BY_EMAIL, userRowMapper, email);
    }

    @Override
    public List<User> findByRole(Integer roleId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_ROLE, userRowMapper, roleId, offset, limit);
    }

    @Override
    public List<Playlist> getPlaylists(Long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_PLAYLISTS, playlistRowMapper, userId, offset, limit);
    }

    @Override
    public List<Artist> getLikedArtists(Long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_ARTISTS, artistRowMapper, offset, limit);
    }

    @Override
    public List<Album> getLikedAlbums(Long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_ALBUMS, albumRowMapper, offset, limit);
    }

    @Override
    public List<Track> getLikedTracks(Long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_TRACKS, trackRowMapper, offset, limit);
    }

    @Override
    public void setRole(Long userId, Integer roleId) throws RepositoryException {
        QueryHelper.update(SQL_SET_ROLE, userId, roleId);
    }

    @Override
    public Optional<Role> getRole(Long userId) {
        return QueryHelper.queryOne(SQL_GET_ROLE, roleRowMapper, userId);
    }

    @Override
    public void addPlaylist(Long userId, Long playlistId) throws RepositoryException {
        QueryHelper.update(SQL_ADD_PLAYLIST, userId, playlistId);
    }

    @Override
    public void removePlayList(Long userId, Long playlistId) throws RepositoryException {
        QueryHelper.update(SQL_REMOVE_PLAYLIST, playlistId);
    }

    @Override
    public void likeArtist(Long userId, Long artistId) throws RepositoryException {
        QueryHelper.update(SQL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public void cancelLikeArtist(Long userId, Long artistId) throws RepositoryException {
        QueryHelper.update(SQL_CANCEL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public void likeAlbum(Long userId, Long albumId) throws RepositoryException {
        QueryHelper.update(SQL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public void cancelLikeAlbum(Long userId, Long albumId) throws RepositoryException {
        QueryHelper.update(SQL_CANCEL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public void likeTrack(Long userId, Long trackId) throws RepositoryException {
        QueryHelper.update(SQL_LIKE_TRACK, userId, trackId);
    }

    @Override
    public void cancelLikeTrack(Long userId, Long trackId) throws RepositoryException {
        QueryHelper.update(SQL_CANCEL_LIKE_TRACK, userId, trackId);
    }
}
