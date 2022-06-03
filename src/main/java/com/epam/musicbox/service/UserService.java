package com.epam.musicbox.service;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service<User> {
    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    List<User> findByRole(Integer roleId, int page) throws ServiceException;

    void setRole(Long userId, Integer roleId) throws ServiceException;

    Optional<Role> getRole(Long userId);

    List<Playlist> getPlaylists(Long userId, int page) throws ServiceException;

    void addPlaylist(Long userId, Long playlistId) throws ServiceException;

    void removePlayList(Long userId, Long playlistId) throws ServiceException;

    List<Artist> getLikedArtists(Long userId, int page) throws ServiceException;

    void likeArtist(Long userId, Long artistId) throws ServiceException;

    void cancelLikeArtist(Long userId, Long artistId) throws ServiceException;

    List<Album> getLikedAlbums(Long userId, int page) throws ServiceException;

    void likeAlbum(Long userId, Long albumId) throws ServiceException;

    void cancelLikeAlbum(Long userId, Long albumId) throws ServiceException;

    List<Track> getLikedTracks(Long userId, int page) throws ServiceException;

    void likeTrack(Long userId, Long trackId) throws ServiceException;

    void cancelLikeTrack(Long userId, Long trackId) throws ServiceException;
}
