package com.epam.musicbox.repository;

import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.entity.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    List<User> findByRole(int roleId, int offset, int limit) throws RepositoryException;

    void setRole(long userId, int roleId) throws RepositoryException;

    Optional<Role> getRole(long userId);

    List<Playlist> getPlaylists(long userId, int offset, int limit) throws RepositoryException;

    void addPlaylist(long userId, long playlistId) throws RepositoryException;

    boolean hasPlaylist(long userId, long playlistId) throws RepositoryException;

    void removePlayList(long userId, long playlistId) throws RepositoryException;

    List<Artist> getLikedArtists(long userId, int offset, int limit) throws RepositoryException;

    void likeArtist(long userId, long artistId) throws RepositoryException;

    boolean isLikeArtist(long userId, long artistId) throws RepositoryException;

    void cancelLikeArtist(long userId, long artistId) throws RepositoryException;


    List<Album> getLikedAlbums(long userId, int offset, int limit) throws RepositoryException;

    void likeAlbum(long userId, long albumId) throws RepositoryException;

    boolean isLikeAlbum(long userId, long albumId) throws RepositoryException;

    void cancelLikeAlbum(long userId, long albumId) throws RepositoryException;


    List<Track> getLikedTracks(long userId, int offset, int limit) throws RepositoryException;

    void likeTrack(long userId, long trackId) throws RepositoryException;

    void cancelLikeTrack(long userId, long trackId) throws RepositoryException;

    boolean isLikeTrack(long userId, long trackId) throws RepositoryException;
}
