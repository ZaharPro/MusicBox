package com.epam.musicbox.service;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService extends EntityService<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    List<User> findByRole(int roleId, int page) throws ServiceException;

    void setRole(long userId, int roleId) throws ServiceException;

    Optional<Role> getRole(long userId);

    List<Playlist> getPlaylists(long userId, int page) throws ServiceException;

    void addPlaylist(long userId, long playlistId) throws ServiceException;

    boolean hasPlaylist(long userId, long playlistId) throws ServiceException;

    void removePlayList(long userId, long playlistId) throws ServiceException;


    List<Artist> getLikedArtists(long userId, int page) throws ServiceException;

    void likeArtist(long userId, long artistId) throws ServiceException;

    boolean isLikeArtist(long userId, long artistId) throws ServiceException;

    void cancelLikeArtist(long userId, long artistId) throws ServiceException;


    List<Album> getLikedAlbums(long userId, int page) throws ServiceException;

    void likeAlbum(long userId, long albumId) throws ServiceException;

    boolean isLikeAlbum(long userId, long albumId) throws ServiceException;

    void cancelLikeAlbum(long userId, long albumId) throws ServiceException;


    List<Track> getLikedTracks(long userId, int page) throws ServiceException;

    void likeTrack(long userId, long trackId) throws ServiceException;

    boolean isLikeTrack(long userId, long trackId) throws ServiceException;

    void cancelLikeTrack(long userId, long trackId) throws ServiceException;
}
