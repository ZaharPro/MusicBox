package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.UserRepository;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.service.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class UserServiceImpl implements UserService {
    @Inject
    private UserRepository userRepository;

    @Override
    public List<User> findPage(int page) throws HttpException {
        return userRepository.findAll(Service.getOffset(page), Service.PAGE_SIZE);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) throws HttpException {
        userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
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
    public List<User> findAllByRole(Integer roleId, int page) throws HttpException {
        return userRepository.findAllByRole(roleId,
                Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public List<User> findAllByStatus(Integer statusId, int page) throws HttpException {
        return userRepository.findAllByStatus(statusId,
                Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public List<Playlist> getPlaylists(Integer userId, int page) throws HttpException {
        return userRepository.getPlaylists(userId,
                Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public List<Artist> getLikedArtists(Integer userId, int page) throws HttpException {
        return userRepository.getLikedArtists(userId,
                Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public List<Album> getLikedAlbums(Integer userId, int page) throws HttpException {
        return userRepository.getLikedAlbums(userId,
                Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public List<Track> getLikedTracks(Integer userId, int page) throws HttpException {
        return userRepository.getLikedTracks(userId,
                Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public void setRole(Integer userId, Integer roleId) throws HttpException {
        userRepository.setRole(userId, roleId);
    }

    @Override
    public Optional<Role> getRole(Integer userId) {
        return userRepository.getRole(userId);
    }

    @Override
    public void setStatus(Integer userId, Integer statusId) throws HttpException {
        userRepository.setStatus(userId, statusId);
    }

    @Override
    public Optional<Status> getStatus(Integer userId) {
        return userRepository.getStatus(userId);
    }

    @Override
    public void addPlaylist(Integer userId, Integer playlistId) throws HttpException {
        userRepository.addPlaylist(userId, playlistId);
    }

    @Override
    public void removePlayList(Integer userId, Integer playlistId) throws HttpException {
        userRepository.removePlayList(userId, playlistId);
    }

    @Override
    public void likeArtist(Integer userId, Integer artistId) throws HttpException {
        userRepository.likeArtist(userId, artistId);
    }

    @Override
    public void cancelLikeArtist(Integer userId, Integer artistId) throws HttpException {
        userRepository.cancelLikeArtist(userId, artistId);
    }

    @Override
    public void likeAlbum(Integer userId, Integer albumId) throws HttpException {
        userRepository.likeAlbum(userId, albumId);
    }

    @Override
    public void cancelLikeAlbum(Integer userId, Integer albumId) throws HttpException {
        userRepository.cancelLikeAlbum(userId, albumId);
    }

    @Override
    public void likeTrack(Integer userId, Integer trackId) throws HttpException {
        userRepository.likeTrack(userId, trackId);
    }

    @Override
    public void cancelLikeTrack(Integer userId, Integer trackId) throws HttpException {
        userRepository.cancelLikeTrack(userId, trackId);
    }
}
