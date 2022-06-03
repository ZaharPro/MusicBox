package com.epam.musicbox.repository;

import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.entity.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User> {
    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    List<User> findByRole(Integer roleId, int offset, int limit) throws RepositoryException;

    void setRole(Long userId, Integer roleId) throws RepositoryException;

    Optional<Role> getRole(Long userId);

    List<Playlist> getPlaylists(Long userId, int offset, int limit) throws RepositoryException;

    void addPlaylist(Long userId, Long playlistId) throws RepositoryException;

    void removePlayList(Long userId, Long playlistId) throws RepositoryException;

    List<Artist> getLikedArtists(Long userId, int offset, int limit) throws RepositoryException;

    void likeArtist(Long userId, Long artistId) throws RepositoryException;

    void cancelLikeArtist(Long userId, Long artistId) throws RepositoryException;

    List<Album> getLikedAlbums(Long userId, int offset, int limit) throws RepositoryException;

    void likeAlbum(Long userId, Long albumId) throws RepositoryException;

    void cancelLikeAlbum(Long userId, Long albumId) throws RepositoryException;

    List<Track> getLikedTracks(Long userId, int offset, int limit) throws RepositoryException;

    void likeTrack(Long userId, Long trackId) throws RepositoryException;

    void cancelLikeTrack(Long userId, Long trackId) throws RepositoryException;
}
