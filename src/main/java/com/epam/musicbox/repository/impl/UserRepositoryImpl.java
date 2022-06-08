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
                                                     "ON user_playlists.playlist_id = playlists.playlist_id " +
                                                     "WHERE user_playlists.user_id=? " +
                                                     "LIMIT ?,?";

    private static final String SQL_ADD_PLAYLIST = "INSERT INTO user_playlists (user_id, playlist_id) " +
                                                   "VALUES (?,?)";

    private static final String SQL_EXIST_PLAYLIST = "SELECT * " +
                                                     "FROM user_playlists " +
                                                     "WHERE user_id=? AND playlist_id=?";

    private static final String SQL_REMOVE_PLAYLIST = "DELETE FROM user_playlists " +
                                                      "WHERE user_id=? AND playlist_id=?";

    private static final String SQL_FIND_LIKED_ARTISTS = "SELECT * " +
                                                         "FROM artists " +
                                                         "JOIN user_liked_artists " +
                                                         "ON user_liked_artists.artist_id = artists.artist_id " +
                                                         "WHERE user_liked_artists.user_id=? " +
                                                         "LIMIT ?,?";

    private static final String SQL_LIKE_ARTIST = "INSERT INTO user_liked_artists (user_id, artist_id) " +
                                                  "VALUES (?,?)";

    private static final String SQL_EXIST_LIKE_ARTIST = "SELECT * " +
                                                       "FROM user_liked_artists " +
                                                       "WHERE user_id=? AND artist_id=?";

    private static final String SQL_CANCEL_LIKE_ARTIST = "DELETE FROM user_liked_artists " +
                                                         "WHERE user_id=? AND artist_id=?";

    private static final String SQL_FIND_LIKED_ALBUMS = "SELECT * " +
                                                        "FROM albums " +
                                                        "JOIN user_liked_albums " +
                                                        "ON user_liked_albums.album_id = albums.album_id " +
                                                        "WHERE user_liked_albums.user_id=? " +
                                                        "LIMIT ?,?";

    private static final String SQL_LIKE_ALBUM = "INSERT INTO user_liked_albums (user_id, album_id) " +
                                                 "VALUES (?,?)";

    private static final String SQL_EXIST_LIKE_ALBUM = "SELECT * " +
                                                       "FROM user_liked_albums " +
                                                       "WHERE user_id=? AND album_id=?";

    private static final String SQL_CANCEL_LIKE_ALBUM = "DELETE FROM user_liked_albums " +
                                                        "WHERE user_id=? AND album_id=?";

    private static final String SQL_FIND_LIKED_TRACKS = "SELECT * " +
                                                        "FROM tracks " +
                                                        "JOIN user_liked_tracks " +
                                                        "ON user_liked_tracks.track_id = tracks.track_id " +
                                                        "WHERE user_liked_tracks.user_id=? " +
                                                        "LIMIT ?,?";

    private static final String SQL_LIKE_TRACK = "INSERT INTO user_liked_tracks (user_id, track_id) " +
                                                 "VALUES (?,?)";

    private static final String SQL_EXIST_LIKE_TRACK = "SELECT * " +
                                                      "FROM user_liked_tracks " +
                                                      "WHERE user_id=? AND track_id=?";

    private static final String SQL_CANCEL_LIKE_TRACK = "DELETE FROM user_liked_tracks " +
                                                        "WHERE user_id=? AND track_id=?";

    public static final UserRepositoryImpl instance = new UserRepositoryImpl();

    private final UserRowMapper userRowMapper = UserRowMapper.getInstance();

    private final PlaylistRowMapper playlistRowMapper = PlaylistRowMapper.getInstance();

    private final ArtistRowMapper artistRowMapper = ArtistRowMapper.getInstance();

    private final AlbumRowMapper albumRowMapper = AlbumRowMapper.getInstance();

    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();

    private final RoleRowMapper roleRowMapper = RoleRowMapper.getInstance();

    private static final Object NULL = new Object();
    private static final RowMapper<?> NULL_MAPPER = (resultSet) -> NULL;

    public static UserRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, userRowMapper, offset, limit);
    }

    @Override
    public Optional<User> findById(long id) {
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
    public void deleteById(long id) throws RepositoryException {
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
    public List<User> findByRole(int roleId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_ROLE, userRowMapper, roleId, offset, limit, roleId);
    }

    @Override
    public List<Playlist> getPlaylists(long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_PLAYLISTS, playlistRowMapper, userId, offset, limit);
    }

    @Override
    public List<Artist> getLikedArtists(long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_ARTISTS, artistRowMapper, userId, offset, limit);
    }

    @Override
    public List<Album> getLikedAlbums(long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_ALBUMS, albumRowMapper, userId, offset, limit);
    }

    @Override
    public List<Track> getLikedTracks(long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_TRACKS, trackRowMapper, userId, offset, limit);
    }

    @Override
    public void setRole(long userId, int roleId) throws RepositoryException {
        QueryHelper.update(SQL_SET_ROLE, userId, roleId);
    }

    @Override
    public Optional<Role> getRole(long userId) {
        return QueryHelper.queryOne(SQL_GET_ROLE, roleRowMapper, userId);
    }

    @Override
    public void addPlaylist(long userId, long playlistId) throws RepositoryException {
        QueryHelper.update(SQL_ADD_PLAYLIST, userId, playlistId);
    }

    @Override
    public boolean hasPlaylist(long userId, long playlistId) throws RepositoryException {
        Optional<?> optional = QueryHelper.queryOne(SQL_EXIST_PLAYLIST, NULL_MAPPER, userId, playlistId);
        return optional.isPresent();
    }

    @Override
    public void removePlayList(long userId, long playlistId) throws RepositoryException {
        QueryHelper.update(SQL_REMOVE_PLAYLIST, userId, playlistId);
    }

    @Override
    public void likeArtist(long userId, long artistId) throws RepositoryException {
        QueryHelper.update(SQL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public boolean isLikeArtist(long userId, long artistId) throws RepositoryException {
        Optional<?> optional = QueryHelper.queryOne(SQL_EXIST_LIKE_ARTIST, NULL_MAPPER, userId, artistId);
        return optional.isPresent();
    }

    @Override
    public void cancelLikeArtist(long userId, long artistId) throws RepositoryException {
        QueryHelper.update(SQL_CANCEL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public void likeAlbum(long userId, long albumId) throws RepositoryException {
        QueryHelper.update(SQL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public boolean isLikeAlbum(long userId, long albumId) throws RepositoryException {
        Optional<?> optional = QueryHelper.queryOne(SQL_EXIST_LIKE_ALBUM, NULL_MAPPER, userId, albumId);
        return optional.isPresent();
    }

    @Override
    public void cancelLikeAlbum(long userId, long albumId) throws RepositoryException {
        QueryHelper.update(SQL_CANCEL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public void likeTrack(long userId, long trackId) throws RepositoryException {
        QueryHelper.update(SQL_LIKE_TRACK, userId, trackId);
    }

    @Override
    public void cancelLikeTrack(long userId, long trackId) throws RepositoryException {
        QueryHelper.update(SQL_CANCEL_LIKE_TRACK, userId, trackId);
    }

    @Override
    public boolean isLikeTrack(long userId, long trackId) throws RepositoryException {
        Optional<?> optional = QueryHelper.queryOne(SQL_EXIST_LIKE_TRACK, NULL_MAPPER, userId, trackId);
        return optional.isPresent();
    }
}
