package com.epam.musicbox.repository;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User> {
    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Integer roleId, int offset, int limit) throws HttpException;

    List<User> findAllByStatus(Integer statusId, int offset, int limit) throws HttpException;

    List<Playlist> getPlaylists(Integer userId, int offset, int limit) throws HttpException;

    List<Artist> getLikedArtists(Integer userId, int offset, int limit) throws HttpException;

    List<Album> getLikedAlbums(Integer userId, int offset, int limit) throws HttpException;

    List<Track> getLikedTracks(Integer userId, int offset, int limit) throws HttpException;

    void setRole(Integer userId, Integer roleId) throws HttpException;

    Optional<Role> getRole(Integer userId);

    void setStatus(Integer userId, Integer statusId) throws HttpException;

    Optional<Status> getStatus(Integer userId);

    void addPlaylist(Integer userId, Integer playlistId) throws HttpException;

    void removePlayList(Integer userId, Integer playlistId) throws HttpException;

    void likeArtist(Integer userId, Integer artistId) throws HttpException;

    void cancelLikeArtist(Integer userId, Integer artistId) throws HttpException;

    void likeAlbum(Integer userId, Integer albumId) throws HttpException;

    void cancelLikeAlbum(Integer userId, Integer albumId) throws HttpException;

    void likeTrack(Integer userId, Integer trackId) throws HttpException;

    void cancelLikeTrack(Integer userId, Integer trackId) throws HttpException;
}
