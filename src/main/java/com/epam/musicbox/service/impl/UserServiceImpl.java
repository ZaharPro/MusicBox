package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.impl.UserRepositoryImpl;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.util.Services;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    public static final UserServiceImpl instance = new UserServiceImpl();

    private final UserRepositoryImpl userRepository = UserRepositoryImpl.getInstance();

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findPage(int page) throws ServiceException {
        try {
            return userRepository.findAll(Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
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
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByRole(int roleId, int page) throws ServiceException {
        try {
            return userRepository.findByRole(roleId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Playlist> getPlaylists(long userId, int page) throws ServiceException {
        try {
            return userRepository.getPlaylists(userId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Artist> getLikedArtists(long userId, int page) throws ServiceException {
        try {
            return userRepository.getLikedArtists(userId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Album> getLikedAlbums(long userId, int page) throws ServiceException {
        try {
            return userRepository.getLikedAlbums(userId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> getLikedTracks(long userId, int page) throws ServiceException {
        try {
            return userRepository.getLikedTracks(userId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
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
    public Optional<Role> getRole(long userId) {
        return userRepository.getRole(userId);
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
