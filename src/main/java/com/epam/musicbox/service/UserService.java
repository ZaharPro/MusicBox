package com.epam.musicbox.service;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;

import java.util.Optional;

public interface UserService extends EntityService<User> {

    Optional<User> findByLogin(String login) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;


    long countByRole(int roleId) throws ServiceException;

    PageSearchResult<User> findByRole(int roleId, int page, int pageSize) throws ServiceException;

    void setRole(long userId, int roleId) throws ServiceException;


    long countLikedTracks(long userId) throws ServiceException;

    PageSearchResult<Track> getLikedTracks(long userId, int page, int pageSize) throws ServiceException;

    boolean isLikedTrack(long userId, long trackId) throws ServiceException;

    void markLikedTrack(long userId, long trackId) throws ServiceException;

    void unmarkLikedTrack(long userId, long trackId) throws ServiceException;


    long countLikedAlbums(long userId) throws ServiceException;

    PageSearchResult<Album> getLikedAlbums(long userId, int page, int pageSize) throws ServiceException;

    boolean isLikedAlbum(long userId, long albumId) throws ServiceException;

    void markLikedAlbum(long userId, long albumId) throws ServiceException;

    void unmarkLikedAlbum(long userId, long albumId) throws ServiceException;


    long countLikedArtists(long userId) throws ServiceException;

    PageSearchResult<Artist> getLikedArtists(long userId, int page, int pageSize) throws ServiceException;

    boolean isLikedArtist(long userId, long artistId) throws ServiceException;

    void markLikedArtist(long userId, long artistId) throws ServiceException;

    void unmarkLikedArtist(long userId, long artistId) throws ServiceException;


    long countPlaylists(long userId) throws ServiceException;

    PageSearchResult<Playlist> getPlaylists(long userId, int page, int pageSize) throws ServiceException;

    boolean hasPlaylist(long userId, long playlistId) throws ServiceException;
}
