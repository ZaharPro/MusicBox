package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.repository.rowmapper.ArtistRowMapper;
import com.epam.musicbox.repository.rowmapper.CountRowMapper;
import com.epam.musicbox.repository.rowmapper.TrackRowMapper;
import com.epam.musicbox.util.QueryHelper;
import com.epam.musicbox.repository.rowmapper.AlbumRowMapper;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.repository.AlbumRepository;

import java.util.List;
import java.util.Optional;

public class AlbumRepositoryImpl implements AlbumRepository {

    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM albums " +
                                               "ORDER BY name " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM albums " +
                                                 "WHERE album_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO albums (name, picture) " +
                                                 "VALUES (?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE albums " +
                                                 "SET name=? picture=? " +
                                                 "WHERE album_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM albums " +
                                                   "WHERE album_id=?";

    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(*) " +
                                                    "FROM albums " +
                                                    "WHERE name REGEXP (?)";

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM albums " +
                                                   "WHERE name REGEXP (?) " +
                                                   "ORDER BY name " +
                                                   "LIMIT ?,?";

    private static final String ALBUM_TABLE = "albums";

    public static final AlbumRepositoryImpl instance = new AlbumRepositoryImpl();

    private final AlbumRowMapper albumRowMapper = AlbumRowMapper.getInstance();

    private final CountRowMapper countRowMapper = CountRowMapper.getInstance();

    private AlbumRepositoryImpl() {
    }

    public static AlbumRepository getInstance() {
        return instance;
    }

    @Override
    public long count() throws RepositoryException {
        return QueryHelper.count(ALBUM_TABLE);
    }

    @Override
    public List<Album> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, albumRowMapper, offset, limit);
    }

    @Override
    public Optional<Album> findById(long id) throws RepositoryException {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, albumRowMapper, id);
    }

    @Override
    public long save(Album artist) throws RepositoryException {
        Long artistId = artist.getId();
        if (artistId == null) {
            return QueryHelper.insert(SQL_INSERT_ONE,
                    artist.getName(),
                    artist.getPicture());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    artist.getName(),
                    artist.getPicture(),
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
    public List<Album> findByName(String regex, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, albumRowMapper, regex, offset, limit);
    }
}
