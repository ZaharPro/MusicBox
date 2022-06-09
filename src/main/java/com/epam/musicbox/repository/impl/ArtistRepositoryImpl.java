package com.epam.musicbox.repository.impl;

import com.epam.musicbox.repository.rowmapper.AlbumRowMapper;
import com.epam.musicbox.repository.rowmapper.ArtistRowMapper;
import com.epam.musicbox.repository.rowmapper.TrackRowMapper;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.entity.*;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.util.QueryHelper;

import java.util.List;
import java.util.Optional;

public class ArtistRepositoryImpl implements ArtistRepository {
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

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM artists " +
                                                   "WHERE name REGEXP (?) " +
                                                   "ORDER BY name " +
                                                   "LIMIT ?,?";

    private static final String SQL_FIND_TRACKS = "SELECT * " +
                                                  "FROM tracks " +
                                                  "JOIN artist_tracks " +
                                                  "ON artist_tracks.track_id = tracks.track_id " +
                                                  "WHERE artist_tracks.artist_id=?" +
                                                  "LIMIT ?,?";

    private static final String SQL_ADD_TRACK = "INSERT INTO artist_tracks (artist_id, track_id) " +
                                                "VALUES (?,?)";

    private static final String SQL_REMOVE_TRACK = "DELETE FROM artist_tracks " +
                                                   "WHERE artist_id=? AND artist_id=?";

    private static final String SQL_FIND_ALBUMS = "SELECT * " +
                                                  "FROM albums " +
                                                  "JOIN tracks " +
                                                  "ON albums.album_id = tracks.album_id " +
                                                  "JOIN artist_tracks " +
                                                  "ON tracks.track_id = artist_tracks.track_id " +
                                                  "WHERE artist_tracks.artist_id=? " +
                                                  "LIMIT ?,?";

    public static final ArtistRepositoryImpl instance = new ArtistRepositoryImpl();

    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();

    private final ArtistRowMapper artistRowMapper = ArtistRowMapper.getInstance();

    private final AlbumRowMapper albumRowMapper = AlbumRowMapper.getInstance();

    public static ArtistRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public List<Artist> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, artistRowMapper, offset, limit);
    }

    @Override
    public Optional<Artist> findById(long id) {
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
    public List<Artist> findByName(String regex, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, artistRowMapper, regex, offset, limit);
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
}
