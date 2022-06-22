package com.epam.musicbox.service.psr;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrackPlaylistPageSearchResult extends PageSearchResult<Track> {

    private final long playlistId;
    private final boolean[] flags;

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

    public TrackPlaylistPageSearchResult(int page, int pageSize) {
        super(page, pageSize);
        this.playlistId = -1;
        this.flags = null;
    }

    public long getPlaylistId() {
        return playlistId;
    }

    public boolean[] getFlags() {
        return flags;
    }

    public static TrackPlaylistPageSearchResult from(PageSearchResult<Track> psr,
                                                     PlaylistService service,
                                                     long playlist) throws ServiceException {
        if (psr.getCount() == 0) {
            return new TrackPlaylistPageSearchResult(psr.getPage(), psr.getPageSize());
        }
        List<Track> elements = psr.getElements();
        boolean[] flags = new boolean[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            Track track = elements.get(i);
            Long id = track.getId();
            boolean hasTrack = service.hasTrack(playlist, id);
            flags[i] = hasTrack;
        }

        return new TrackPlaylistPageSearchResult(psr.getPage(),
                psr.getPageSize(),
                psr.getCount(),
                elements,
                playlist,
                flags);
    }
}
