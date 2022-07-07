package com.epam.musicbox.service.page;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;

import java.util.List;

/**
 * The type Track playlist page search result.
 */
public class TrackPlaylistPageSearchResult extends PageSearchResult<Track> {

    private final long playlistId;
    private final boolean[] flags;

    /**
     * Instantiates a new Track playlist page search result.
     *
     * @param page       the page
     * @param pageSize   the page size
     * @param count      the count
     * @param elements   the elements
     * @param playlistId the playlist id
     * @param flags      the flags
     */
    public TrackPlaylistPageSearchResult(int page,
                                         int pageSize,
                                         long count,
                                         List<Track> elements,
                                         long playlistId,
                                         boolean[] flags) {
        super(page, pageSize, count, elements);
        this.playlistId = playlistId;
        this.flags = flags;
    }

    /**
     * Instantiates a new Track playlist page search result.
     *
     * @param page     the page
     * @param pageSize the page size
     */
    public TrackPlaylistPageSearchResult(int page, int pageSize) {
        super(page, pageSize);
        this.playlistId = -1;
        this.flags = null;
    }

    /**
     * Gets playlist id.
     *
     * @return the playlist id
     */
    public long getPlaylistId() {
        return playlistId;
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
     * From track playlist page search result.
     *
     * @param psr      the page search result
     * @param service  the playlist service
     * @param playlistId the playlist id
     * @return the track playlist page search result
     * @throws ServiceException the service exception
     */
    public static TrackPlaylistPageSearchResult from(PageSearchResult<Track> psr,
                                                     PlaylistService service,
                                                     long playlistId) throws ServiceException {
        if (psr.getCount() == 0) {
            return new TrackPlaylistPageSearchResult(psr.getPage(), psr.getPageSize());
        }
        List<Track> elements = psr.getElements();
        boolean[] flags = new boolean[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            Track track = elements.get(i);
            Long id = track.getId();
            boolean hasTrack = service.hasTrack(playlistId, id);
            flags[i] = hasTrack;
        }

        return new TrackPlaylistPageSearchResult(psr.getPage(),
                psr.getPageSize(),
                psr.getCount(),
                elements,
                playlistId,
                flags);
    }
}
