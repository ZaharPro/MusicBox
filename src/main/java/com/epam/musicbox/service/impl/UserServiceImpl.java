package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.UserRepository;
import com.epam.musicbox.repository.impl.UserRepositoryImpl;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl extends AbstractEntityService<User> implements UserService {

    private static final UserServiceImpl instance = new UserServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();
    private final UserRepository repository = UserRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    protected UserRepository getRepository() {
        return repository;
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        if (!validator.isValidLogin(login)) {
            return Optional.empty();
        }
        try {
            return getRepository().findByLogin(login);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        if (!validator.isValidEmail(email)) {
            return Optional.empty();
        }
        try {
            return getRepository().findByEmail(email);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countByRole(int roleId) throws ServiceException {
        try {
            return getRepository().countByRole(roleId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageSearchResult<User> findByRole(int roleId, int page, int pageSize) throws ServiceException {
        try {
            UserRepository repository = getRepository();
            long count = repository.countByRole(roleId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<User> list = repository.findByRole(roleId, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setRole(long userId, int roleId) throws ServiceException {
        try {
            getRepository().setRole(userId, roleId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Role> getRole(long userId) throws ServiceException {
        try {
            return getRepository().getRole(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countPlaylists(long userId) throws ServiceException {
        try {
            return getRepository().countPlaylists(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageSearchResult<Playlist> getPlaylists(long userId, int page, int pageSize) throws ServiceException {
        try {
            UserRepository repository = getRepository();
            long count = repository.countPlaylists(userId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Playlist> list = repository.getPlaylists(userId, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean hasPlaylist(long userId, long playlistId) throws ServiceException {
        try {
            return getRepository().hasPlaylist(userId, playlistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addPlaylist(long userId, long playlistId) throws ServiceException {
        try {
            getRepository().addPlaylist(userId, playlistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removePlaylist(long userId, long playlistId) throws ServiceException {
        try {
            getRepository().removePlaylist(userId, playlistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countLikedArtists(long userId) throws ServiceException {
        try {
            return getRepository().countLikedArtists(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageSearchResult<Artist> getLikedArtists(long userId, int page, int pageSize) throws ServiceException {
        try {
            UserRepository repository = getRepository();
            long count = repository.countLikedArtists(userId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Artist> list = repository.getLikedArtists(userId, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isLikedArtist(long userId, long artistId) throws ServiceException {
        try {
            return getRepository().isLikedArtist(userId, artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void markLikedArtist(long userId, long artistId) throws ServiceException {
        try {
            getRepository().markLikedArtist(userId, artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unmarkLikedArtist(long userId, long artistId) throws ServiceException {
        try {
            getRepository().unmarkLikedArtist(userId, artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countLikedAlbums(long userId) throws ServiceException {
        try {
            return getRepository().countLikedAlbums(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageSearchResult<Album> getLikedAlbums(long userId, int page, int pageSize) throws ServiceException {
        try {
            UserRepository repository = getRepository();
            long count = repository.countLikedAlbums(userId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Album> list = repository.getLikedAlbums(userId, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isLikedAlbum(long userId, long albumId) throws ServiceException {
        try {
            return getRepository().isLikedAlbum(userId, albumId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void markLikedAlbum(long userId, long albumId) throws ServiceException {
        try {
            getRepository().markLikedAlbum(userId, albumId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unmarkLikedAlbum(long userId, long albumId) throws ServiceException {
        try {
            getRepository().unmarkLikedAlbum(userId, albumId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countLikedTracks(long userId) throws ServiceException {
        try {
            return getRepository().countLikedTracks(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageSearchResult<Track> getLikedTracks(long userId, int page, int pageSize) throws ServiceException {
        try {
            UserRepository repository = getRepository();
            long count = repository.countLikedTracks(userId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Track> list = repository.getLikedTracks(userId, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isLikedTrack(long userId, long trackId) throws ServiceException {
        try {
            return getRepository().isLikedTrack(userId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void markLikedTrack(long userId, long trackId) throws ServiceException {
        try {
            getRepository().markLikedTrack(userId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unmarkLikedTrack(long userId, long trackId) throws ServiceException {
        try {
            getRepository().unmarkLikedTrack(userId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
