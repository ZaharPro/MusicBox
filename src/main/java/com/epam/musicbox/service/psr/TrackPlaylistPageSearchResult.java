package com.epam.musicbox.service.psr;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.PlaylistService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TrackPlaylistPageSearchResult extends PageSearchResult<Track> {

    private final long playlistId;
    private final List<Boolean> flags;

    public TrackPlaylistPageSearchResult(int page,
                                         int pageSize,
                                         long count,
                                         List<Track> elements,
                                         long playlistId,
                                         List<Boolean> flags) {
        super(page, pageSize, count, elements);
        this.playlistId = playlistId;
        this.flags = flags;
    }

    public TrackPlaylistPageSearchResult(int page, int pageSize) {
        super(page, pageSize);
        this.playlistId = -1;
        this.flags = Collections.emptyList();
    }

    public long getPlaylistId() {
        return playlistId;
    }

    public List<Boolean> getFlags() {
        return flags;
    }

    public static TrackArtistPageSearchResult from(PageSearchResult<Track> psr,
                                                   PlaylistService service,
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
