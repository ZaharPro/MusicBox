package com.epam.musicbox.repository;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User> {
    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    List<User> findByRole(Integer roleId, int offset, int limit) throws HttpException;

    void setRole(Long userId, Integer roleId) throws HttpException;

    Optional<Role> getRole(Long userId);

    List<Playlist> getPlaylists(Long userId, int offset, int limit) throws HttpException;

    void addPlaylist(Long userId, Long playlistId) throws HttpException;

    void removePlayList(Long userId, Long playlistId) throws HttpException;

    List<Artist> getLikedArtists(Long userId, int offset, int limit) throws HttpException;

    void likeArtist(Long userId, Long artistId) throws HttpException;

    void cancelLikeArtist(Long userId, Long artistId) throws HttpException;

    List<Album> getLikedAlbums(Long userId, int offset, int limit) throws HttpException;

    void likeAlbum(Long userId, Long albumId) throws HttpException;

    void cancelLikeAlbum(Long userId, Long albumId) throws HttpException;

    List<Track> getLikedTracks(Long userId, int offset, int limit) throws HttpException;

    void likeTrack(Long userId, Long trackId) throws HttpException;

    void cancelLikeTrack(Long userId, Long trackId) throws HttpException;
}
