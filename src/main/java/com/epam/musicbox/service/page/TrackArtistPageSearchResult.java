package com.epam.musicbox.service.page;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;

import java.util.List;

/**
 * The type Track artist page search result.
 */
public class TrackArtistPageSearchResult extends PageSearchResult<Track> {

    private final long artistId;
    private final boolean[] flags;

    /**
     * Instantiates a new Track artist page search result.
     *
     * @param page     the page
     * @param pageSize the page size
     * @param count    the count
     * @param elements the elements
     * @param artistId the artist id
     * @param flags    the flags
     */
    public TrackArtistPageSearchResult(int page,
                                       int pageSize,
                                       long count,
                                       List<Track> elements,
                                       long artistId,
                                       boolean[] flags) {
        super(page, pageSize, count, elements);
        this.artistId = artistId;
        this.flags = flags;
    }

    /**
     * Instantiates a new Track artist page search result.
     *
     * @param page     the page
     * @param pageSize the page size
     */
    public TrackArtistPageSearchResult(int page, int pageSize) {
        super(page, pageSize);
        this.artistId = -1;
        this.flags = null;
    }

    /**
     * Gets album id.
     *
     * @return the album id
     */
    public long getAlbumId() {
        return artistId;
    }

    /**
     * Get flags boolean array.
     *
     * @return the boolean array
     */
    public boolean[] getFlags() {
        return flags;
    }

    /**
     * From track artist page search result.
     *
     * @param psr      the page search result
     * @param service  the artist service
     * @param artistId the artist id
     * @return the track artist page search result
     * @throws ServiceException the service exception
     */
    public static TrackArtistPageSearchResult from(PageSearchResult<Track> psr,
                                                   ArtistService service,
                                                   long artistId) throws ServiceException {
        if (psr.getCount() == 0) {
            return new TrackArtistPageSearchResult(psr.getPage(), psr.getPageSize());
        }
        List<Track> elements = psr.getElements();
        boolean[] flags = new boolean[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            Track track = elements.get(i);
            Long id = track.getId();
            boolean hasTrack = service.hasTrack(artistId, id);
            flags[i] = hasTrack;
        }

        return new TrackArtistPageSearchResult(psr.getPage(),
                psr.getPageSize(),
                psr.getCount(),
                elements,
                artistId,
                flags);
    }
}
