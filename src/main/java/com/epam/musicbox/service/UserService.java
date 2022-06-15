package com.epam.musicbox.service;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService extends EntityService<User> {

    Optional<User> findByLogin(String login) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;


    long countByRole(int roleId) throws ServiceException;

    List<User> findByRole(int roleId, int page, int pageSize) throws ServiceException;

    void setRole(long userId, int roleId) throws ServiceException;

    Optional<Role> getRole(long userId) throws ServiceException;


    long countPlaylists(long userId) throws ServiceException;

    List<Playlist> getPlaylists(long userId, int page, int pageSize) throws ServiceException;

    void addPlaylist(long userId, long playlistId) throws ServiceException;

    boolean hasPlaylist(long userId, long playlistId) throws ServiceException;

    void removePlayList(long userId, long playlistId) throws ServiceException;


    long countLikedArtists(long userId) throws ServiceException;

    List<Artist> getLikedArtists(long userId, int page, int pageSize) throws ServiceException;

    void likeArtist(long userId, long artistId) throws ServiceException;

    boolean isLikeArtist(long userId, long artistId) throws ServiceException;

    void cancelLikeArtist(long userId, long artistId) throws ServiceException;


    long countLikedAlbums(long userId) throws ServiceException;

    List<Album> getLikedAlbums(long userId, int page, int pageSize) throws ServiceException;

    void likeAlbum(long userId, long albumId) throws ServiceException;

    boolean isLikeAlbum(long userId, long albumId) throws ServiceException;

    void cancelLikeAlbum(long userId, long albumId) throws ServiceException;


    long countLikedTracks(long userId) throws ServiceException;

    List<Track> getLikedTracks(long userId, int page, int pageSize) throws ServiceException;

    void likeTrack(long userId, long trackId) throws ServiceException;

    boolean isLikeTrack(long userId, long trackId) throws ServiceException;

    void cancelLikeTrack(long userId, long trackId) throws ServiceException;
}
