package com.epam.musicbox.repository.impl;

import com.epam.musicbox.repository.rowmapper.CountRowMapper;
import com.epam.musicbox.util.QueryHelper;
import com.epam.musicbox.repository.rowmapper.AlbumRowMapper;
import com.epam.musicbox.repository.rowmapper.ArtistRowMapper;
import com.epam.musicbox.repository.rowmapper.TrackRowMapper;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.entity.*;
import com.epam.musicbox.repository.ArtistRepository;

import java.util.List;
import java.util.Optional;

public class ArtistRepositoryImpl implements ArtistRepository {

    private static final String SQL_COUNT = "SELECT COUNT(*) " +
                                            "FROM artists";

    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM artists " +
                                               "ORDER BY name " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM artists " +
                                                 "WHERE artist_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO artists (name, avatar) " +
                                                 "VALUES (?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE artists " +
                                                 "SET name=? avatar=? " +
                                                 "WHERE artist_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM artists " +
                                                   "WHERE artist_id=?";

    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(*) " +
                                                    "FROM artists " +
                                                    "WHERE name REGEXP (?)";

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM artists " +
                                                   "WHERE name REGEXP (?) " +
                                                   "ORDER BY name " +
                                                   "LIMIT ?,?";

    private static final String SQL_COUNT_TRACKS = "SELECT COUNT(*) " +
                                                   "FROM tracks " +
                                                   "JOIN artist_tracks " +
                                                   "ON artist_tracks.track_id = tracks.track_id " +
                                                   "WHERE artist_tracks.artist_id=?";

    private static final String SQL_FIND_TRACKS = "SELECT * " +
                                                  "FROM tracks " +
                                                  "JOIN artist_tracks " +
                                                  "ON artist_tracks.track_id = tracks.track_id " +
                                                  "WHERE artist_tracks.artist_id=? " +
                                                  "LIMIT ?,?";

    private static final String SQL_ADD_TRACK = "IF EXISTS " +
                                                "(SELECT 1 FROM artist_tracks WHERE artist_id=? AND track_id=?) " +
                                                "BEGIN " +
                                                "INSERT INTO artist_tracks (artist_id, track_id) " +
                                                "VALUES (?,?) " +
                                                "END";

    private static final String SQL_REMOVE_TRACK = "DELETE FROM artist_tracks " +
                                                   "WHERE artist_id=? AND track_id=?";

    private static final String SQL_COUNT_ALBUMS = "SELECT COUNT(*) " +
                                                   "FROM albums " +
                                                   "JOIN tracks " +
                                                   "ON albums.album_id = tracks.album_id " +
                                                   "JOIN artist_tracks " +
                                                   "ON tracks.track_id = artist_tracks.track_id " +
                                                   "WHERE artist_tracks.artist_id=? ";

    private static final String SQL_FIND_ALBUMS = "SELECT * " +
                                                  "FROM albums " +
                                                  "JOIN tracks " +
                                                  "ON albums.album_id = tracks.album_id " +
                                                  "JOIN artist_tracks " +
                                                  "ON tracks.track_id = artist_tracks.track_id " +
                                                  "WHERE artist_tracks.artist_id=? " +
                                                  "LIMIT ?,?";

    private static final ArtistRepositoryImpl instance = new ArtistRepositoryImpl();

    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();

    private final ArtistRowMapper artistRowMapper = ArtistRowMapper.getInstance();

    private final AlbumRowMapper albumRowMapper = AlbumRowMapper.getInstance();

    private final CountRowMapper countRowMapper = CountRowMapper.getInstance();

    private ArtistRepositoryImpl() {
    }

    public static ArtistRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws RepositoryException {
        return QueryHelper.queryOne(SQL_COUNT, countRowMapper).orElse(0L);
    }

    @Override
    public List<Artist> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, artistRowMapper, offset, limit);
    }

    @Override
    public Optional<Artist> findById(long id) throws RepositoryException {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, artistRowMapper, id);
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
    public long countByName(String regex) throws RepositoryException {
        Optional<Long> optionalCount = QueryHelper.queryOne(SQL_COUNT_BY_NAME, countRowMapper, regex);
        return optionalCount.orElse(0L);
    }

    @Override
    public List<Artist> findByName(String regex, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, artistRowMapper, regex, offset, limit);
    }

    @Override
    public long countTracks(long artistId) throws RepositoryException {
        Optional<Long> optionalCount = QueryHelper.queryOne(SQL_COUNT_TRACKS, countRowMapper, artistId);
        return optionalCount.orElse(0L);
    }

    @Override
    public List<Track> getTracks(long artistId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_TRACKS, trackRowMapper, artistId, offset, limit);
    }

    @Override
    public void addTrack(long artistId, long trackId) throws RepositoryException {
        QueryHelper.update(SQL_ADD_TRACK, artistId, trackId);
    }

    @Override
    public void removeTrack(long artistId, long trackId) throws RepositoryException {
        QueryHelper.update(SQL_REMOVE_TRACK, artistId, trackId);
    }

    @Override
    public List<Album> getAlbums(long artistId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALBUMS, albumRowMapper, artistId, offset, limit);
    }

    @Override
    public long countAlbums(long artistId) throws RepositoryException {
        Optional<Long> optionalCount = QueryHelper.queryOne(SQL_COUNT_ALBUMS, countRowMapper, artistId);
        return optionalCount.orElse(0L);
    }
}
