package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.UserRepository;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.service.UserService;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findPage(int page) throws HttpException {
        return userRepository.findAll(Service.getOffset(page), Service.PAGE_SIZE);
    }

    @Override
    public Optional<User> findById(Integer id) throws HttpException {
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
    public Optional<User> findByLogin(String login) throws HttpException {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> findByEmail(String email) throws HttpException {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) throws HttpException {
        return userRepository.findByLoginAndPassword(login, password);
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
}
