package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.impl.UserRepositoryImpl;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.util.ServiceUtils;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final UserServiceImpl instance = new UserServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final UserRepositoryImpl userRepository = UserRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws ServiceException {
        try {
            return userRepository.count();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findPage(int page, int pageSize) throws ServiceException {
        try {
            return userRepository.findAll(ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findById(long id) throws ServiceException {
        try {
            return userRepository.findById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long save(User user) throws ServiceException {
        try {
            return userRepository.save(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            userRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        if (!validator.isValidLogin(login)) {
            return Optional.empty();
        }
        try {
            return userRepository.findByLogin(login);
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
            return userRepository.findByEmail(email);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countByRole(int roleId) throws ServiceException {
        try {
            return userRepository.countByRole(roleId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findByRole(int roleId, int page, int pageSize) throws ServiceException {
        try {
            return userRepository.findByRole(roleId,
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Playlist> getPlaylists(long userId, int page, int pageSize) throws ServiceException {
        try {
            return userRepository.getPlaylists(userId,
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Artist> getLikedArtists(long userId, int page, int pageSize) throws ServiceException {
        try {
            return userRepository.getLikedArtists(userId,
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Album> getLikedAlbums(long userId, int page, int pageSize) throws ServiceException {
        try {
            return userRepository.getLikedAlbums(userId,
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> getLikedTracks(long userId, int page, int pageSize) throws ServiceException {
        try {
            return userRepository.getLikedTracks(userId,
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setRole(long userId, int roleId) throws ServiceException {
        try {
            userRepository.setRole(userId, roleId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Role> getRole(long userId) throws ServiceException {
        try {
            return userRepository.getRole(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countPlaylists(long userId) throws ServiceException {
        try {
            return userRepository.countPlaylists(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addPlaylist(long userId, long playlistId) throws ServiceException {
        try {
            userRepository.addPlaylist(userId, playlistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean hasPlaylist(long userId, long playlistId) throws ServiceException {
        try {
            return userRepository.hasPlaylist(userId, playlistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removePlayList(long userId, long playlistId) throws ServiceException {
        try {
            userRepository.removePlayList(userId, playlistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countLikedArtists(long userId) throws ServiceException {
        try {
            return userRepository.countLikedArtists(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void likeArtist(long userId, long artistId) throws ServiceException {
        try {
            userRepository.likeArtist(userId, artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isLikeArtist(long userId, long artistId) throws ServiceException {
        try {
            return userRepository.isLikeArtist(userId, artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cancelLikeArtist(long userId, long artistId) throws ServiceException {
        try {
            userRepository.cancelLikeArtist(userId, artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countLikedAlbums(long userId) throws ServiceException {
        try {
            return userRepository.countLikedAlbums(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void likeAlbum(long userId, long albumId) throws ServiceException {
        try {
            userRepository.likeAlbum(userId, albumId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isLikeAlbum(long userId, long albumId) throws ServiceException {
        try {
            return userRepository.isLikeAlbum(userId, albumId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cancelLikeAlbum(long userId, long albumId) throws ServiceException {
        try {
            userRepository.cancelLikeAlbum(userId, albumId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countLikedTracks(long userId) throws ServiceException {
        try {
            return userRepository.countLikedTracks(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void likeTrack(long userId, long trackId) throws ServiceException {
        try {
            userRepository.likeTrack(userId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isLikeTrack(long userId, long trackId) throws ServiceException {
        try {
            return userRepository.isLikeTrack(userId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cancelLikeTrack(long userId, long trackId) throws ServiceException {
        try {
            userRepository.cancelLikeTrack(userId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
