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
    public Optional<User> findById(Long id) {
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
    public void deleteById(Long id) throws ServiceException {
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
    public List<User> findByRole(Integer roleId, int page) throws ServiceException {
        try {
            return userRepository.findByRole(roleId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Playlist> getPlaylists(Long userId, int page) throws ServiceException {
        try {
            return userRepository.getPlaylists(userId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Artist> getLikedArtists(Long userId, int page) throws ServiceException {
        try {
            return userRepository.getLikedArtists(userId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Album> getLikedAlbums(Long userId, int page) throws ServiceException {
        try {
            return userRepository.getLikedAlbums(userId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> getLikedTracks(Long userId, int page) throws ServiceException {
        try {
            return userRepository.getLikedTracks(userId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setRole(Long userId, Integer roleId) throws ServiceException {
        try {
            userRepository.setRole(userId, roleId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Role> getRole(Long userId) {
        return userRepository.getRole(userId);
    }

    @Override
    public void addPlaylist(Long userId, Long playlistId) throws ServiceException {
        try {
            userRepository.addPlaylist(userId, playlistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removePlayList(Long userId, Long playlistId) throws ServiceException {
        try {
            userRepository.removePlayList(userId, playlistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void likeArtist(Long userId, Long artistId) throws ServiceException {
        try {
            userRepository.likeArtist(userId, artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cancelLikeArtist(Long userId, Long artistId) throws ServiceException {
        try {
            userRepository.cancelLikeArtist(userId, artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void likeAlbum(Long userId, Long albumId) throws ServiceException {
        try {
            userRepository.likeAlbum(userId, albumId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cancelLikeAlbum(Long userId, Long albumId) throws ServiceException {
        try {
            userRepository.cancelLikeAlbum(userId, albumId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void likeTrack(Long userId, Long trackId) throws ServiceException {
        try {
            userRepository.likeTrack(userId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cancelLikeTrack(Long userId, Long trackId) throws ServiceException {
        try {
            userRepository.cancelLikeTrack(userId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
