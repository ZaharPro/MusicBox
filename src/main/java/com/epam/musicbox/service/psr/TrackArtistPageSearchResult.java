package com.epam.musicbox.service.psr;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrackArtistPageSearchResult extends PageSearchResult<Track> {

    private final long artistId;
    private final List<Boolean> flags;

    public TrackArtistPageSearchResult(int page,
                                       int pageSize,
                                       long count,
                                       List<Track> elements,
                                       long artistId,
                                       List<Boolean> flags) {
        super(page, pageSize, count, elements);
        this.artistId = artistId;
        this.flags = flags;
    }

    public TrackArtistPageSearchResult(int page, int pageSize) {
        super(page, pageSize);
        this.artistId = -1;
        this.flags = Collections.emptyList();
    }

    public long getAlbumId() {
        return artistId;
    }

    public List<Boolean> getFlags() {
        return flags;
    }

    public static TrackArtistPageSearchResult from(PageSearchResult<Track> psr,
                                                   ArtistService service,
                                                   long artistId) throws ServiceException {
        if (psr.getCount() == 0) {
            return new TrackArtistPageSearchResult(psr.getPage(), psr.getPageSize());
        }
        List<Track> elements = psr.getElements();
        List<Boolean> flags = new ArrayList<>(elements.size());
        for (Track track : elements) {
            Long id = track.getId();
            Boolean hasTrack = service.hasTrack(artistId, id);
            flags.add(hasTrack);
        }

        return new TrackArtistPageSearchResult(psr.getPage(),
                psr.getPageSize(),
                psr.getCount(),
                elements,
                artistId,
                flags);
    }
}
