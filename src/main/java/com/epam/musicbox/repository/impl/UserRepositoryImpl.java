package com.epam.musicbox.repository.impl;

import com.epam.musicbox.util.QueryHelper;
import com.epam.musicbox.repository.rowmapper.*;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.entity.*;
import com.epam.musicbox.repository.UserRepository;

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

    private static final String SQL_UPDATE_ONE = "UPDATE users " +
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

    private static final String SQL_COUNT_BY_ROLE = "SELECT COUNT(*) " +
                                                    "FROM users " +
                                                    "JOIN user_roles " +
                                                    "ON user_roles.role_id = roles.role_id " +
                                                    "WHERE user_roles.user_id=?";

    private static final String SQL_FIND_BY_ROLE = "SELECT * " +
                                                   "FROM users " +
                                                   "JOIN user_roles " +
                                                   "ON user_roles.role_id = roles.role_id " +
                                                   "WHERE user_roles.user_id=? " +
                                                   "LIMIT ?,?";

    private static final String SQL_INSERT_ROLE = "INSERT INTO user_roles (user_id, role_id) " +
                                                  "VALUES (?,?)";

    private static final String SQL_UPDATE_ROLE = "UPDATE users " +
                                                  "SET user_id, role_id=? ";

    private static final String SQL_GET_ROLE = "SELECT * " +
                                               "FROM roles " +
                                               "JOIN user_roles " +
                                               "ON user_roles.role_id = roles.role_id " +
                                               "WHERE user_roles.user_id=?";

    private static final String SQL_COUNT_PLAYLISTS = "SELECT COUNT(*) " +
                                                      "FROM playlists " +
                                                      "JOIN user_playlists " +
                                                      "ON user_playlists.playlist_id = playlists.playlist_id " +
                                                      "WHERE user_playlists.user_id=?";

    private static final String SQL_FIND_PLAYLISTS = "SELECT * " +
                                                     "FROM playlists " +
                                                     "JOIN user_playlists " +
                                                     "ON user_playlists.playlist_id = playlists.playlist_id " +
                                                     "WHERE user_playlists.user_id=? " +
                                                     "LIMIT ?,?";

    private static final String SQL_ADD_PLAYLIST = "INSERT INTO user_playlists (user_id, playlist_id) " +
                                                   "VALUES (?,?)";

    private static final String SQL_REMOVE_PLAYLIST = "DELETE FROM user_playlists " +
                                                      "WHERE user_id=? AND playlist_id=?";

    private static final String SQL_COUNT_LIKED_ARTISTS = "SELECT COUNT(*) " +
                                                          "FROM artists " +
                                                          "JOIN user_liked_artists " +
                                                          "ON user_liked_artists.artist_id = artists.artist_id " +
                                                          "WHERE user_liked_artists.user_id=? ";

    private static final String SQL_FIND_LIKED_ARTISTS = "SELECT * " +
                                                         "FROM artists " +
                                                         "JOIN user_liked_artists " +
                                                         "ON user_liked_artists.artist_id = artists.artist_id " +
                                                         "WHERE user_liked_artists.user_id=? " +
                                                         "LIMIT ?,?";

    private static final String SQL_LIKE_ARTIST = "INSERT INTO user_liked_artists (user_id, artist_id) " +
                                                  "VALUES (?,?)";

    private static final String SQL_CANCEL_LIKE_ARTIST = "DELETE FROM user_liked_artists " +
                                                         "WHERE user_id=? AND artist_id=?";

    private static final String SQL_COUNT_LIKED_ALBUMS = "SELECT * " +
                                                         "FROM albums " +
                                                         "JOIN user_liked_albums " +
                                                         "ON user_liked_albums.album_id = albums.album_id " +
                                                         "WHERE user_liked_albums.user_id=?";

    private static final String SQL_FIND_LIKED_ALBUMS = "SELECT * " +
                                                        "FROM albums " +
                                                        "JOIN user_liked_albums " +
                                                        "ON user_liked_albums.album_id = albums.album_id " +
                                                        "WHERE user_liked_albums.user_id=? " +
                                                        "LIMIT ?,?";

    private static final String SQL_LIKE_ALBUM = "INSERT INTO user_liked_albums (user_id, album_id) " +
                                                 "VALUES (?,?)";

    private static final String SQL_CANCEL_LIKE_ALBUM = "DELETE FROM user_liked_albums " +
                                                        "WHERE user_id=? AND album_id=?";

    private static final String SQL_COUNT_LIKED_TRACKS = "SELECT COUNT(*) " +
                                                         "FROM tracks " +
                                                         "JOIN user_liked_tracks " +
                                                         "ON user_liked_tracks.track_id = tracks.track_id " +
                                                         "WHERE user_liked_tracks.user_id=?";

    private static final String SQL_FIND_LIKED_TRACKS = "SELECT * " +
                                                        "FROM tracks " +
                                                        "JOIN user_liked_tracks " +
                                                        "ON user_liked_tracks.track_id = tracks.track_id " +
                                                        "WHERE user_liked_tracks.user_id=? " +
                                                        "LIMIT ?,?";

    private static final String SQL_LIKE_TRACK = "INSERT INTO user_liked_tracks (user_id, track_id) " +
                                                 "VALUES (?,?)";

    private static final String SQL_CANCEL_LIKE_TRACK = "DELETE FROM user_liked_tracks " +
                                                        "WHERE user_id=? AND track_id=?";

    private static final String USER_TABLE = "users";

    private static final String USER_ROLES_TABLE = "user_roles";
    private static final String USER_ROLE_CONDITION = "user_id=? AND role_id=?";

    private static final String USER_PLAYLISTS_TABLE = "user_playlists";
    private static final String USER_PLAYLIST_CONDITION = "user_id=? AND playlist_id=?";

    private static final String USER_ARTISTS_TABLE = "user_liked_artists";
    private static final String USER_ARTISTS_CONDITION = "user_id=? AND artist_id=?";

    private static final String USER_ALBUMS_TABLE = "user_liked_albums";
    private static final String USER_ALBUMS_CONDITION = "user_id=? AND album_id=?";

    private static final String USER_TRACKS_TABLE = "user_liked_tracks";
    private static final String USER_TRACKS_CONDITION = "user_id=? AND track_id=?";

    public static final UserRepositoryImpl instance = new UserRepositoryImpl();

    private final UserRowMapper userRowMapper = UserRowMapper.getInstance();

    private final PlaylistRowMapper playlistRowMapper = PlaylistRowMapper.getInstance();

    private final ArtistRowMapper artistRowMapper = ArtistRowMapper.getInstance();

    private final AlbumRowMapper albumRowMapper = AlbumRowMapper.getInstance();

    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();

    private final RoleRowMapper roleRowMapper = RoleRowMapper.getInstance();

    private final CountRowMapper countRowMapper = CountRowMapper.getInstance();

    private UserRepositoryImpl() {
    }

    public static UserRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws RepositoryException {
        return QueryHelper.count(USER_TABLE);
    }

    @Override
    public List<User> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, userRowMapper, offset, limit);
    }

    @Override
    public Optional<User> findById(long id) throws RepositoryException {
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
    public Optional<User> findByLogin(String login) throws RepositoryException {
        return QueryHelper.queryOne(SQL_FIND_BY_LOGIN, userRowMapper, login);
    }

    @Override
    public Optional<User> findByEmail(String email) throws RepositoryException {
        return QueryHelper.queryOne(SQL_FIND_BY_EMAIL, userRowMapper, email);
    }

    @Override
    public long countByRole(int roleId) throws RepositoryException {
        Optional<Long> optionalCount = QueryHelper.queryOne(SQL_COUNT_BY_ROLE, countRowMapper, roleId);
        return optionalCount.orElse(0L);
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
        boolean exist = QueryHelper.exist(USER_ROLES_TABLE, USER_ROLE_CONDITION, userId, roleId);
        String sql = exist ? SQL_UPDATE_ROLE : SQL_INSERT_ROLE;
        QueryHelper.update(sql, userId, roleId);
    }

    @Override
    public Optional<Role> getRole(long userId) throws RepositoryException {
        return QueryHelper.queryOne(SQL_GET_ROLE, roleRowMapper, userId);
    }

    @Override
    public long countPlaylists(long userId) throws RepositoryException {
        Optional<Long> optionalCount = QueryHelper.queryOne(SQL_COUNT_PLAYLISTS, countRowMapper, userId);
        return optionalCount.orElse(0L);
    }

    @Override
    public void addPlaylist(long userId, long playlistId) throws RepositoryException {
        QueryHelper.update(SQL_ADD_PLAYLIST, userId, playlistId);
    }

    @Override
    public boolean hasPlaylist(long userId, long playlistId) throws RepositoryException {
        return QueryHelper.exist(USER_PLAYLISTS_TABLE, USER_PLAYLIST_CONDITION, userId, playlistId);
    }

    @Override
    public void removePlayList(long userId, long playlistId) throws RepositoryException {
        QueryHelper.update(SQL_REMOVE_PLAYLIST, userId, playlistId);
    }

    @Override
    public long countLikedArtists(long userId) throws RepositoryException {
        Optional<Long> optionalCount = QueryHelper.queryOne(SQL_COUNT_LIKED_ARTISTS, countRowMapper, userId);
        return optionalCount.orElse(0L);
    }

    @Override
    public void likeArtist(long userId, long artistId) throws RepositoryException {
        QueryHelper.update(SQL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public boolean isLikeArtist(long userId, long artistId) throws RepositoryException {
        return QueryHelper.exist(USER_ARTISTS_TABLE, USER_ARTISTS_CONDITION, userId, artistId);
    }

    @Override
    public void cancelLikeArtist(long userId, long artistId) throws RepositoryException {
        QueryHelper.update(SQL_CANCEL_LIKE_ARTIST, userId, artistId);
    }

    @Override
    public long countLikedAlbums(long userId) throws RepositoryException {
        Optional<Long> optionalCount = QueryHelper.queryOne(SQL_COUNT_LIKED_ALBUMS, countRowMapper, userId);
        return optionalCount.orElse(0L);
    }

    @Override
    public void likeAlbum(long userId, long albumId) throws RepositoryException {
        QueryHelper.update(SQL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public boolean isLikeAlbum(long userId, long albumId) throws RepositoryException {
        return QueryHelper.exist(USER_ALBUMS_TABLE, USER_ALBUMS_CONDITION, userId, albumId);
    }

    @Override
    public void cancelLikeAlbum(long userId, long albumId) throws RepositoryException {
        QueryHelper.update(SQL_CANCEL_LIKE_ALBUM, userId, albumId);
    }

    @Override
    public long countLikedTracks(long userId) throws RepositoryException {
        Optional<Long> optionalCount = QueryHelper.queryOne(SQL_COUNT_LIKED_TRACKS, countRowMapper, userId);
        return optionalCount.orElse(0L);
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
        return QueryHelper.exist(USER_TRACKS_TABLE, USER_TRACKS_CONDITION, userId, trackId);
    }
}
