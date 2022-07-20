package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.repository.UserRepository;
import com.epam.musicbox.repository.rowmapper.*;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_COUNT = "SELECT COUNT(u.user_id) " +
                                            "FROM users AS u";

    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM users AS u " +
                                               "ORDER BY u.login " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM users AS u " +
                                                 "WHERE u.user_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO users (login, password, email, role_id, banned) " +
                                                 "VALUES (?,?,?,?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE users " +
                                                 "SET login=?, password=?, email=?, role_id=?, banned=? " +
                                                 "WHERE user_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM users " +
                                                   "WHERE user_id=?";

    private static final String SQL_FIND_BY_LOGIN = "SELECT * " +
                                                    "FROM users AS u " +
                                                    "WHERE u.login=?";

    private static final String SQL_FIND_BY_EMAIL = "SELECT * " +
                                                    "FROM users AS u " +
                                                    "WHERE u.email=?";

    private static final String SQL_COUNT_BY_ROLE = "SELECT COUNT(u.user_id) " +
                                                    "FROM users AS u " +
                                                    "WHERE u.role_id=?";

    private static final String SQL_FIND_BY_ROLE = "SELECT * " +
                                                   "FROM users AS u " +
                                                   "WHERE u.role_id=? " +
                                                   "ORDER BY u.login " +
                                                   "LIMIT ?,?";

    private static final String SQL_UPDATE_ROLE = "UPDATE users " +
                                                  "SET role_id=? " +
                                                  "WHERE user_id=?";

    //playlists
    private static final String SQL_COUNT_PLAYLISTS = "SELECT COUNT(p.playlist_id) " +
                                                      "FROM playlists AS p " +
                                                      "WHERE p.user_id=?";

    private static final String SQL_EXIST_PLAYLIST = "SELECT 1 " +
                                                     "FROM playlists AS p " +
                                                     "WHERE p.user_id=? AND p.playlist_id=?";

    private static final String SQL_FIND_PLAYLISTS = "SELECT * " +
                                                     "FROM playlists AS p " +
                                                     "WHERE p.user_id=? " +
                                                     "ORDER BY p.name " +
                                                     "LIMIT ?,?";

    private static final String SQL_COUNT_LIKED_ARTISTS = "SELECT COUNT(a.artist_id) " +
                                                          "FROM artists AS a " +
                                                          "JOIN user_liked_artists AS ula " +
                                                          "ON ula.artist_id = a.artist_id " +
                                                          "WHERE ula.user_id=? ";

    private static final String SQL_FIND_LIKED_ARTISTS = "SELECT * " +
                                                         "FROM artists AS a " +
                                                         "JOIN user_liked_artists AS ula " +
                                                         "ON ula.artist_id = a.artist_id " +
                                                         "WHERE ula.user_id=? " +
                                                         "ORDER BY a.name " +
                                                         "LIMIT ?,?";

    //liked artist
    private static final String SQL_EXIST_LIKED_ARTIST = "SELECT 1 " +
                                                         "FROM user_liked_artists " +
                                                         "WHERE user_id=? AND artist_id=?";

    private static final String SQL_MARK_LIKED_ARTIST = "INSERT INTO user_liked_artists (user_id, artist_id) " +
                                                        "VALUES (?,?)";

    private static final String SQL_UNMARK_LIKED_ARTIST = "DELETE FROM user_liked_artists " +
                                                          "WHERE user_id=? AND artist_id=?";

    private static final String SQL_COUNT_LIKED_ALBUMS = "SELECT * " +
                                                         "FROM albums AS a " +
                                                         "JOIN user_liked_albums AS ula " +
                                                         "ON ula.album_id = a.album_id " +
                                                         "WHERE ula.user_id=?";

    //liked albums
    private static final String SQL_FIND_LIKED_ALBUMS = "SELECT * " +
                                                        "FROM albums AS a " +
                                                        "JOIN user_liked_albums AS ula " +
                                                        "ON ula.album_id = a.album_id " +
                                                        "WHERE ula.user_id=? " +
                                                        "ORDER BY a.name " +
                                                        "LIMIT ?,?";

    private static final String SQL_EXIST_LIKED_ALBUM = "SELECT 1 " +
                                                        "FROM user_liked_albums " +
                                                        "WHERE user_id=? AND album_id=?";

    private static final String SQL_MARK_LIKED_ALBUM = "INSERT INTO user_liked_albums (user_id, album_id) " +
                                                       "VALUES (?,?)";

    private static final String SQL_UNMARK_LIKED_ALBUM = "DELETE FROM user_liked_albums " +
                                                         "WHERE user_id=? AND album_id=?";

    //liked tracks
    private static final String SQL_COUNT_LIKED_TRACKS = "SELECT COUNT(t.track_id) " +
                                                         "FROM tracks AS t " +
                                                         "JOIN user_liked_tracks AS ult " +
                                                         "ON ult.track_id = t.track_id " +
                                                         "WHERE ult.user_id=?";

    private static final String SQL_EXIST_LIKED_TRACK = "SELECT 1 " +
                                                        "FROM user_liked_tracks " +
                                                        "WHERE user_id=? AND track_id=?";

    private static final String SQL_FIND_LIKED_TRACKS = "SELECT * " +
                                                        "FROM tracks AS t " +
                                                        "JOIN user_liked_tracks AS ult " +
                                                        "ON ult.track_id = t.track_id " +
                                                        "WHERE ult.user_id=? " +
                                                        "ORDER BY t.name " +
                                                        "LIMIT ?,?";

    private static final String SQL_MARK_LIKED_TRACK = "INSERT INTO user_liked_tracks (user_id, track_id) " +
                                                       "VALUES (?,?)";

    private static final String SQL_UNMARK_LIKED_TRACK = "DELETE FROM user_liked_tracks " +
                                                         "WHERE user_id=? AND track_id=?";

    public static final UserRepositoryImpl instance = new UserRepositoryImpl();

    private final UserRowMapper userRowMapper = UserRowMapper.getInstance();
    private final PlaylistRowMapper playlistRowMapper = PlaylistRowMapper.getInstance();
    private final ArtistRowMapper artistRowMapper = ArtistRowMapper.getInstance();
    private final AlbumRowMapper albumRowMapper = AlbumRowMapper.getInstance();
    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();
    private final CountRowMapper countRowMapper = CountRowMapper.getInstance();
    private final BooleanRowMapper booleanRowMapper = BooleanRowMapper.getInstance();

    private UserRepositoryImpl() {
    }

    public static UserRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws RepositoryException {
        return QueryHelper.query(SQL_COUNT, countRowMapper).orElse(0L);
    }

    @Override
    public List<User> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, userRowMapper, offset, limit);
    }

    @Override
    public Optional<User> findById(long id) throws RepositoryException {
        return QueryHelper.query(SQL_FIND_BY_ID, userRowMapper, id);
    }

    @Override
    public long save(User user) throws RepositoryException {
        Long userId = user.getId();
        if (userId == null) {
            return QueryHelper.insert(SQL_INSERT_ONE,
                    user.getLogin(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getRole().getId(),
                    user.getBanned());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    user.getLogin(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getRole().getId(),
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
        return QueryHelper.query(SQL_FIND_BY_LOGIN, userRowMapper, login);
    }

    @Override
    public Optional<User> findByEmail(String email) throws RepositoryException {
        return QueryHelper.query(SQL_FIND_BY_EMAIL, userRowMapper, email);
    }


    @Override
    public long countByRole(int roleId) throws RepositoryException {
        return QueryHelper.query(SQL_COUNT_BY_ROLE, countRowMapper, roleId).orElse(0L);
    }

    @Override
    public List<User> findByRole(int roleId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_ROLE, userRowMapper, roleId, offset, limit);
    }

    @Override
    public void setRole(long userId, int roleId) throws RepositoryException {
        QueryHelper.update(SQL_UPDATE_ROLE, roleId, userId);
    }

    @Override
    public long countLikedTracks(long userId) throws RepositoryException {
        return QueryHelper.query(SQL_COUNT_LIKED_TRACKS, countRowMapper, userId).orElse(0L);
    }

    @Override
    public List<Track> getLikedTracks(long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_TRACKS, trackRowMapper, userId, offset, limit);
    }

    @Override
    public boolean isLikedTrack(long userId, long trackId) throws RepositoryException {
        return QueryHelper.query(SQL_EXIST_LIKED_TRACK, booleanRowMapper, userId, trackId).orElse(false);
    }

    @Override
    public void markLikedTrack(long userId, long trackId) throws RepositoryException {
        if (!isLikedTrack(userId, trackId)) {
            QueryHelper.update(SQL_MARK_LIKED_TRACK, userId, trackId);
        }
    }

    @Override
    public void unmarkLikedTrack(long userId, long trackId) throws RepositoryException {
        QueryHelper.update(SQL_UNMARK_LIKED_TRACK, userId, trackId);
    }


    @Override
    public long countLikedAlbums(long userId) throws RepositoryException {
        return QueryHelper.query(SQL_COUNT_LIKED_ALBUMS, countRowMapper, userId).orElse(0L);
    }

    @Override
    public List<Album> getLikedAlbums(long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_ALBUMS, albumRowMapper, userId, offset, limit);
    }

    @Override
    public boolean isLikedAlbum(long userId, long albumId) throws RepositoryException {
        return QueryHelper.query(SQL_EXIST_LIKED_ALBUM, booleanRowMapper, userId, albumId).orElse(false);
    }

    @Override
    public void markLikedAlbum(long userId, long albumId) throws RepositoryException {
        if (!isLikedAlbum(userId, albumId)) {
            QueryHelper.update(SQL_MARK_LIKED_ALBUM, userId, albumId);
        }
    }

    @Override
    public void unmarkLikedAlbum(long userId, long albumId) throws RepositoryException {
        QueryHelper.update(SQL_UNMARK_LIKED_ALBUM, userId, albumId);
    }


    @Override
    public long countLikedArtists(long userId) throws RepositoryException {
        return QueryHelper.query(SQL_COUNT_LIKED_ARTISTS, countRowMapper, userId).orElse(0L);
    }

    @Override
    public List<Artist> getLikedArtists(long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_LIKED_ARTISTS, artistRowMapper, userId, offset, limit);
    }

    @Override
    public boolean isLikedArtist(long userId, long artistId) throws RepositoryException {
        return QueryHelper.query(SQL_EXIST_LIKED_ARTIST, booleanRowMapper, userId, artistId).orElse(false);
    }

    @Override
    public void markLikedArtist(long userId, long artistId) throws RepositoryException {
        if (!isLikedArtist(userId, artistId)) {
            QueryHelper.update(SQL_MARK_LIKED_ARTIST, userId, artistId);
        }
    }

    @Override
    public void unmarkLikedArtist(long userId, long artistId) throws RepositoryException {
        QueryHelper.update(SQL_UNMARK_LIKED_ARTIST, userId, artistId);
    }


    @Override
    public long countPlaylists(long userId) throws RepositoryException {
        return QueryHelper.query(SQL_COUNT_PLAYLISTS, countRowMapper, userId).orElse(0L);
    }

    @Override
    public List<Playlist> getPlaylists(long userId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_PLAYLISTS, playlistRowMapper, userId, offset, limit);
    }

    @Override
    public boolean hasPlaylist(long userId, long playlistId) throws RepositoryException {
        return QueryHelper.query(SQL_EXIST_PLAYLIST, booleanRowMapper, userId, playlistId).orElse(false);
    }
}
