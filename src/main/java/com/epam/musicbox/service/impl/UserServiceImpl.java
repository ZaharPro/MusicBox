package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.HttpException;
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
    public List<User> findPage(int page) throws HttpException {
        return userRepository.findAll(Services.getOffset(page), Services.PAGE_SIZE);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) throws HttpException {
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) throws HttpException {
        userRepository.deleteById(id);
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
    public List<User> findByRole(Integer roleId, int page) throws HttpException {
        return userRepository.findByRole(roleId, Services.getOffset(page), Services.PAGE_SIZE);
    }

    @Override
    public List<Playlist> getPlaylists(Long userId, int page) throws HttpException {
        return userRepository.getPlaylists(userId, Services.getOffset(page), Services.PAGE_SIZE);
    }

    @Override
    public List<Artist> getLikedArtists(Long userId, int page) throws HttpException {
        return userRepository.getLikedArtists(userId, Services.getOffset(page), Services.PAGE_SIZE);
    }

    @Override
    public List<Album> getLikedAlbums(Long userId, int page) throws HttpException {
        return userRepository.getLikedAlbums(userId, Services.getOffset(page), Services.PAGE_SIZE);
    }

    @Override
    public List<Track> getLikedTracks(Long userId, int page) throws HttpException {
        return userRepository.getLikedTracks(userId, Services.getOffset(page), Services.PAGE_SIZE);
    }

    @Override
    public void setRole(Long userId, Integer roleId) throws HttpException {
        userRepository.setRole(userId, roleId);
    }

    @Override
    public Optional<Role> getRole(Long userId) {
        return userRepository.getRole(userId);
    }

    @Override
    public void addPlaylist(Long userId, Long playlistId) throws HttpException {
        userRepository.addPlaylist(userId, playlistId);
    }

    @Override
    public void removePlayList(Long userId, Long playlistId) throws HttpException {
        userRepository.removePlayList(userId, playlistId);
    }

    @Override
    public void likeArtist(Long userId, Long artistId) throws HttpException {
        userRepository.likeArtist(userId, artistId);
    }

    @Override
    public void cancelLikeArtist(Long userId, Long artistId) throws HttpException {
        userRepository.cancelLikeArtist(userId, artistId);
    }

    @Override
    public void likeAlbum(Long userId, Long albumId) throws HttpException {
        userRepository.likeAlbum(userId, albumId);
    }

    @Override
    public void cancelLikeAlbum(Long userId, Long albumId) throws HttpException {
        userRepository.cancelLikeAlbum(userId, albumId);
    }

    @Override
    public void likeTrack(Long userId, Long trackId) throws HttpException {
        userRepository.likeTrack(userId, trackId);
    }

    @Override
    public void cancelLikeTrack(Long userId, Long trackId) throws HttpException {
        userRepository.cancelLikeTrack(userId, trackId);
    }
}
