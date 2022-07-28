package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.repository.rowmapper.*;

import java.util.List;
import java.util.Optional;

public class ArtistRepositoryImpl implements ArtistRepository {

    private static final String SQL_COUNT = "SELECT COUNT(a.artist_id) " +
                                            "FROM artists AS a";

    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM artists AS a " +
                                               "ORDER BY a.name " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM artists AS a " +
                                                 "WHERE a.artist_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO artists (name, avatar) " +
                                                 "VALUES (?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE artists " +
                                                 "SET name=?, avatar=? " +
                                                 "WHERE artist_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM artists " +
                                                   "WHERE artist_id=?";

    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(a.artist_id) " +
                                                    "FROM artists AS a " +
                                                    "WHERE a.name LIKE CONCAT('%',?,'%')";

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM artists AS a " +
                                                   "WHERE a.name LIKE CONCAT('%',?,'%') " +
                                                   "ORDER BY a.name " +
                                                   "LIMIT ?,?";

    private static final String SQL_COUNT_TRACKS = "SELECT COUNT(t.track_id) " +
                                                   "FROM tracks AS t " +
                                                   "JOIN artist_tracks AS at " +
                                                   "ON at.track_id = t.track_id " +
                                                   "WHERE at.artist_id=?";

    private static final String SQL_FIND_TRACKS = "SELECT * " +
                                                  "FROM tracks AS t " +
                                                  "JOIN artist_tracks AS at " +
                                                  "ON at.track_id = t.track_id " +
                                                  "WHERE at.artist_id=? " +
                                                  "ORDER BY t.name " +
                                                  "LIMIT ?,?";

    private static final String SQL_EXIST_TRACK = "SELECT 1 " +
                                                  "FROM artist_tracks AS at " +
                                                  "WHERE at.artist_id=? AND at.track_id=?";

    private static final String SQL_ADD_TRACK = "INSERT INTO artist_tracks (artist_id, track_id) " +
                                                "VALUES (?,?)";

    private static final String SQL_REMOVE_TRACK = "DELETE FROM artist_tracks " +
                                                   "WHERE artist_id=? AND track_id=?";

    private static final String SQL_COUNT_ALBUMS = "SELECT COUNT(DISTINCT a.album_id) " +
                                                   "FROM albums AS a " +
                                                   "JOIN tracks AS t " +
                                                   "ON a.album_id = t.album_id " +
                                                   "JOIN artist_tracks AS at " +
                                                   "ON t.track_id = at.track_id " +
                                                   "WHERE at.artist_id=? ";

    private static final String SQL_FIND_ALBUMS = "SELECT DISTINCT a.album_id, a.name, a.picture " +
                                                  "FROM albums AS a " +
                                                  "JOIN tracks AS t " +
                                                  "ON a.album_id = t.album_id " +
                                                  "JOIN artist_tracks AS at " +
                                                  "ON t.track_id = at.track_id " +
                                                  "WHERE at.artist_id=? " +
                                                  "ORDER BY a.name " +
                                                  "LIMIT ?,?";

    private static final ArtistRepositoryImpl instance = new ArtistRepositoryImpl();

    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();
    private final ArtistRowMapper artistRowMapper = ArtistRowMapper.getInstance();
    private final AlbumRowMapper albumRowMapper = AlbumRowMapper.getInstance();
    private final CountRowMapper countRowMapper = CountRowMapper.getInstance();
    private final BooleanRowMapper booleanRowMapper = BooleanRowMapper.getInstance();

    private ArtistRepositoryImpl() {
    }

    public static ArtistRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws RepositoryException {
        return QueryHelper.query(SQL_COUNT, countRowMapper).orElse(0L);
    }

    @Override
    public List<Artist> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, artistRowMapper, offset, limit);
    }

    @Override
    public Optional<Artist> findById(long id) throws RepositoryException {
        return QueryHelper.query(SQL_FIND_BY_ID, artistRowMapper, id);
    }

    @Override
    public long save(Artist artist) throws RepositoryException {
        Long artistId = artist.getId();
        if (artistId == null) {
            return QueryHelper.insert(SQL_INSERT_ONE,
                    artist.getName(),
                    artist.getAvatar());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    artist.getName(),
                    artist.getAvatar(),
                    artistId);
            return artistId;
        }
    }

    @Override
    public void deleteById(long id) throws RepositoryException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public long countByName(String name) throws RepositoryException {
        return QueryHelper.query(SQL_COUNT_BY_NAME, countRowMapper, name).orElse(0L);
    }

    @Override
    public List<Artist> findByName(String name, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, artistRowMapper, name, offset, limit);
    }

    @Override
    public long countTracks(long artistId) throws RepositoryException {
        return QueryHelper.query(SQL_COUNT_TRACKS, countRowMapper, artistId).orElse(0L);
    }

    @Override
    public List<Track> getTracks(long artistId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_TRACKS, trackRowMapper, artistId, offset, limit);
    }

    @Override
    public boolean hasTrack(long artistId, long trackId) throws RepositoryException {
        return QueryHelper.query(SQL_EXIST_TRACK, booleanRowMapper, artistId, trackId).orElse(false);
    }

    @Override
    public void addTrack(long artistId, long trackId) throws RepositoryException {
        if (!hasTrack(artistId, trackId)) {
            QueryHelper.update(SQL_ADD_TRACK, artistId, trackId);
        }
    }

    @Override
    public void removeTrack(long artistId, long trackId) throws RepositoryException {
        QueryHelper.update(SQL_REMOVE_TRACK, artistId, trackId);
    }

    @Override
    public long countAlbums(long artistId) throws RepositoryException {
        return QueryHelper.query(SQL_COUNT_ALBUMS, countRowMapper, artistId).orElse(0L);
    }

    @Override
    public List<Album> getAlbums(long artistId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALBUMS, albumRowMapper, artistId, offset, limit);
    }
}
