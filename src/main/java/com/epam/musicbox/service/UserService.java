package com.epam.musicbox.service;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.HttpException;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service<User> {
    Optional<User> findByLogin(String login) throws HttpException;

    Optional<User> findByEmail(String email) throws HttpException;

    Optional<User> findByLoginAndPassword(String login, String password) throws HttpException;

    List<User> findAllByRole(Integer roleId, int page) throws HttpException;

    List<User> findAllByStatus(Integer statusId, int page) throws HttpException;

    List<Playlist> getPlaylists(Integer userId, int page) throws HttpException;

    List<Artist> getLikedArtists(Integer userId, int page) throws HttpException;

    List<Album> getLikedAlbums(Integer userId, int page) throws HttpException;

    List<Track> getLikedTracks(Integer userId, int page) throws HttpException;
}
