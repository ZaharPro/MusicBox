package com.epam.musicbox.entity;

import java.io.Serializable;

/**
 * The type Track.
 */
public class Track implements Entity, Serializable {

    private static final long serialVersionUID = -3330908376356894184L;
    private Long id;
    private String name;
    private String audio;
    private long albumId;

    /**
     * Instantiates a new Track.
     */
    public Track() {
    }

    /**
     * Instantiates a new Track.
     *
     * @param id      the id
     * @param name    the name
     * @param audio   the audio
     * @param albumId the album id
     */
    public Track(Long id, String name, String audio, long albumId) {
        this.id = id;
        this.name = name;
        this.albumId = albumId;
        this.audio = audio;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets album id.
     *
     * @return the album id
     */
    public long getAlbumId() {
        return albumId;
    }

    /**
     * Sets album id.
     *
     * @param albumId the album id
     */
    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    /**
     * Gets audio.
     *
     * @return the audio
     */
    public String getAudio() {
        return audio;
    }

    /**
     * Sets audio.
     *
     * @param audio the audio
     */
    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        if (!(id == null ? track.id == null : id.equals(track.id)))
            return false;
        if (!(name == null ? track.name == null : name.equals(track.name)))
            return false;
        if (!(audio == null ? track.audio == null : audio.equals(track.audio)))
            return false;
        return albumId == track.albumId;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (id == null ? 0 : id.hashCode());
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (audio == null ? 0 : audio.hashCode());
        hash = hash * 31 + Long.hashCode(albumId);
        return hash;
    }

    @Override
    public String toString() {
        return new StringBuilder("Track{")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", audio='").append(audio).append('\'')
                .append(", albumId=").append(albumId)
                .append('}')
                .toString();
    }
}
