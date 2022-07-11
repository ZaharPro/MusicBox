package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.repository.AlbumRepository;
import com.epam.musicbox.repository.rowmapper.AlbumRowMapper;
import com.epam.musicbox.repository.rowmapper.CountRowMapper;

import java.util.List;
import java.util.Optional;

public class AlbumRepositoryImpl implements AlbumRepository {

    private static final String SQL_COUNT = "SELECT COUNT(a.album_id) " +
                                            "FROM albums AS a";

    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM albums AS a " +
                                               "ORDER BY a.name " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM albums AS a " +
                                                 "WHERE a.album_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO albums (name, picture) " +
                                                 "VALUES (?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE albums " +
                                                 "SET name=?, picture=? " +
                                                 "WHERE album_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM albums " +
                                                   "WHERE album_id=?";

    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(a.album_id) " +
                                                    "FROM albums AS a " +
                                                    "WHERE a.name LIKE CONCAT('%',?,'%')";

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM albums AS a " +
                                                   "WHERE a.name LIKE CONCAT('%',?,'%') " +
                                                   "ORDER BY a.name " +
                                                   "LIMIT ?,?";

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
        return QueryHelper.queryOne(SQL_COUNT, countRowMapper).orElse(0L);
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
    public long countByName(String name) throws RepositoryException {
        return QueryHelper.queryOne(SQL_COUNT_BY_NAME, countRowMapper, name).orElse(0L);
    }

    @Override
    public List<Album> findByName(String name, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, albumRowMapper, name, offset, limit);
    }
}
